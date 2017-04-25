package com.photochecker.models.lka;

import com.photochecker.models.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

/**
 * Created by market6 on 24.03.2017.
 */
public class LkaExpert {


    private static LocalDate today = LocalDate.now();
    private static LocalDate startDate;
    private static LocalDate endDate;

    public static void setStartDate(LocalDate startDate) {
        LkaExpert.startDate = startDate;
    }

    public static void setEndDate(LocalDate endDate) {
        LkaExpert.endDate = endDate;
    }



    public static LocalDate getInitialStartDate() {
        startDate = today.minusDays(2);
        return startDate;
    }

    public static LocalDate getInitialEndDate() {
        endDate = today.minusDays(2);
        return endDate;
    }

    public static Map<Integer, String> getRegionMap(User user) {
        Map<Integer, String> regionMap = new TreeMap<>();
        try {
            Connection connection = DataSourcePhotochecker.getDataSource().getConnection();
            PreparedStatement statement = connection.prepareStatement("select distinct r.`region_name`, r.`region_id` from `region_db` r\n" +
                    "inner join `client_card` cc on cc.`region_id` = r.`region_id`\n" +
                    "inner join `photo_card` pc on pc.`client_id` = cc.`client_id`\n" +
                    "where pc.`date` >= ? and pc.`date` < ?\n" +
                    "order by 1;");
            statement.setDate(1, Date.valueOf(startDate));
            statement.setDate(2, Date.valueOf(endDate));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                regionMap.put(resultSet.getInt("region_id"), resultSet.getString("region_name").trim());
            }
            resultSet.close();
            statement.close();

            if (user.getRole() == 1) {
                Statement statement1 = connection.createStatement();

                ResultSet resultSet1 = statement1.executeQuery("SELECT DISTINCT r.`region_id`\n" +
                        "FROM `responsibility_db` res\n" +
                        "INNER JOIN `distr_db` d ON res.`distr_id` = d.`distr_id`\n" +
                        "INNER JOIN `region_db` r ON d.`region_id` = r.`region_id`\n" +
                        "WHERE\n" +
                        "res.`report_type` = 5\n" +
                        "AND res.`resp_user` = " + user.getId());

                Set<Integer> regionAllowed = new HashSet<>();
                while (resultSet1.next()) {
                    regionAllowed.add(resultSet1.getInt("region_id"));
                }

                regionMap.entrySet().removeIf(entry -> !regionAllowed.contains(entry.getKey()));

                resultSet1.close();
                statement1.close();
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return regionMap;
    }

    public static Map<Integer, String> getDistrMap(int regionId, User user) {
        Map<Integer, String> distrMap = new TreeMap<>();
        try {
            Connection connection = DataSourcePhotochecker.getDataSource().getConnection();
            PreparedStatement statement = connection.prepareStatement("select distinct d.`distr_name`, d.`distr_id` from `distr_db` d\n" +
                    "inner join `client_card` cc on cc.`distributor_id` = d.`distr_id`\n" +
                    "inner join `photo_card` pc on pc.`client_id` = cc.`client_id`\n" +
                    "where pc.`date` >= ? and pc.`date` < ?\n" +
                    "and cc.`region_id` = ?\n" +
                    "order by 1;");
            statement.setDate(1, Date.valueOf(startDate));
            statement.setDate(2, Date.valueOf(endDate));
            statement.setInt(3, regionId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                distrMap.put(resultSet.getInt("distr_id"), resultSet.getString("distr_name").trim());
            }
            resultSet.close();
            statement.close();

            if (user.getRole() == 1) {
                Statement statement1 = connection.createStatement();

                ResultSet resultSet1 = statement1.executeQuery("SELECT DISTINCT res.`distr_id`\n" +
                        "FROM `responsibility_db` res\n" +
                        "WHERE\n" +
                        "res.`report_type` = 5\n" +
                        "AND res.`resp_user` = " + user.getId());

                Set<Integer> distrAllowed = new HashSet<>();
                while (resultSet1.next()) {
                    distrAllowed.add(resultSet1.getInt("distr_id"));
                }

                distrMap.entrySet().removeIf(entry -> !distrAllowed.contains(entry.getKey()));

                resultSet1.close();
                statement1.close();
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return distrMap;
    }

    public static Map<Integer, String> getLkaMap(int regionId, int distrId) {
        Map<Integer, String> lkaMap = new TreeMap<>();
        try {
            Connection connection = DataSourcePhotochecker.getDataSource().getConnection();
            PreparedStatement statement = connection.prepareStatement("select distinct lka.`lka_name`, lka.`lka_id` from `lka_db` lka\n" +
                    "inner join `client_card` cc on cc.`lka_id` = lka.`lka_id`\n" +
                    "inner join `photo_card` pc on pc.`client_id` = cc.`client_id`\n" +
                    "where pc.`date` >= ? and pc.`date` < ?\n" +
                    "and cc.`region_id` = ?\n" +
                    "and cc.`distributor_id` = ?\n" +
                    "order by 1;");
            statement.setDate(1, Date.valueOf(startDate));
            statement.setDate(2, Date.valueOf(endDate));
            statement.setInt(3, regionId);
            statement.setInt(4, distrId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                lkaMap.put(resultSet.getInt("lka_id"), resultSet.getString("lka_name"));
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lkaMap;
    }

    public static List<ClientCard> getClientList(int regionId, int distrId, int lkaId) {
        List<ClientCard> clientCardList = new ArrayList<>();
        try {
            Connection connection = DataSourcePhotochecker.getDataSource().getConnection();
            PreparedStatement statement = connection.prepareStatement("select distinct cc.`client_id`, cc.`client_name`, cc.`client_address`, cc.`type_name`, case when sav.`save_date` is not null then 1 else 0 end checked\n" +
                    "from `client_card` cc\n" +
                    "inner join `photo_card` pc on pc.`client_id` = cc.`client_id`\n" +
                    "left join (select * from save_lka_db slka where slka.date_from=? and slka.date_to=?) sav on sav.client_id=cc.client_id\n" +
                    "where pc.`date` >= ? and pc.`date` < ?\n" +
                    "and cc.`region_id` = ?\n" +
                    "and cc.`distributor_id` = ?\n" +
                    "and cc.`lka_id` = ?\n" +
                    "order by 1;");
            statement.setDate(1, Date.valueOf(startDate));
            statement.setDate(2, Date.valueOf(endDate.minusDays(1)));
            statement.setDate(3, Date.valueOf(startDate));
            statement.setDate(4, Date.valueOf(endDate));
            statement.setInt(5, regionId);
            statement.setInt(6, distrId);
            statement.setInt(7, lkaId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ClientCard clientCard = new ClientCard(resultSet.getInt("client_id"),
                        resultSet.getString("client_name"),
                        resultSet.getString("client_address"),
                        resultSet.getString("type_name"),
                        resultSet.getBoolean("checked"));
                clientCardList.add(clientCard);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientCardList;
    }

    public static List<PhotoCard> getPhotoList (int clientId) {
        List<PhotoCard> photoCardList = new ArrayList<>();
        try {
            Connection connection = DataSourcePhotochecker.getDataSource().getConnection();
            PreparedStatement statement = connection.prepareStatement("select pc.`url`, pc.`date`, pc.`date_add`, pc.`comment`, pc.`checked`\n" +
                    "from `photo_card` pc\n" +
                    "inner join `client_card` cc on cc.`client_id` = pc.`client_id`\n" +
                    "where pc.`date` >= ? and pc.`date` < ?\n" +
                    "and cc.`client_id` = ?\n" +
                    "order by 2;");
            statement.setDate(1, Date.valueOf(startDate));
            statement.setDate(2, Date.valueOf(endDate));
            statement.setInt(3, clientId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String comment = resultSet.getString("comment");
                if (comment == null || comment.equals("null")) comment = "";
                PhotoCard photoCard = new PhotoCard(resultSet.getString("url"),
                        resultSet.getTimestamp("date").toLocalDateTime().toLocalDate(),
                        resultSet.getTimestamp("date_add").toLocalDateTime().toLocalDate(),
                        comment,
                        resultSet.getBoolean("checked"));
                photoCardList.add(photoCard);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return photoCardList;
    }

    public static LkaCriterias getLkaCriterias(int lkaId) {
        LkaCriterias lkaCriterias = null;
        try {
            Connection connection = DataSourcePhotochecker.getDataSource().getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT lk.lka_name, cr.* FROM `lka_criterias_db` cr\n" +
                    "inner join `lka_db` lk on lk.`lka_id` = cr.`lka_id`\n" +
                    "WHERE cr.`lka_id` = ?");
            statement.setInt(1, lkaId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                lkaCriterias = new LkaCriterias(
                        resultSet.getString("lka_name"),
                        lkaId,
                        resultSet.getString("crit1_name"),
                        resultSet.getInt("crit1_mz"),
                        resultSet.getInt("crit1_k"),
                        resultSet.getInt("crit1_s"),
                        resultSet.getInt("crit1_m"),
                        resultSet.getString("crit2_name"));
            } else {
                lkaCriterias = new LkaCriterias("", lkaId, "Доля полки",
                        10, 10, 10, 10,
                        "Бренд-блок");
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lkaCriterias;
    }

    public static List<LkaCriterias> getAllLkaCriterias() {
        List<LkaCriterias> lkaCriteriasList = new ArrayList<>();
        try {
            Connection connection = DataSourcePhotochecker.getDataSource().getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT lk.`lka_name`, cr.* FROM `lka_criterias_db` cr\n" +
                    "left join `lka_db` lk on lk.`lka_id` = cr.`lka_id`\n" +
                    "order by 1");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                LkaCriterias lkaCriterias = new LkaCriterias(
                        resultSet.getString("lka_name") != null ? resultSet.getString("lka_name") : "",
                        resultSet.getInt("lka_id"),
                        resultSet.getString("crit1_name"),
                        resultSet.getInt("crit1_mz"),
                        resultSet.getInt("crit1_k"),
                        resultSet.getInt("crit1_s"),
                        resultSet.getInt("crit1_m"),
                        resultSet.getString("crit2_name"));
                lkaCriteriasList.add(lkaCriterias);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lkaCriteriasList;
    }

    public static boolean saveCriterias (ClientCriterias clientCriterias) {
        boolean isSucceed = false;
        try {
            Connection connection = DataSourcePhotochecker.getDataSource().getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO `save_lka_db`\n" +
                    "(`client_id`, `date_from`, `date_to`, `save_date`, " +
                    "`has_mz`, `has_photo_mz`, `is_correct_mz`, `has_add_prod_mz`, `crit1_mz`, `crit2_mz`, " +
                    "`has_k`, `has_photo_k`, `is_correct_k`, `crit1_k`, `crit2_k`, " +
                    "`has_s`, `has_photo_s`, `is_correct_s`, `crit1_s`, `crit2_s`, " +
                    "`has_m`, `has_photo_m`, `is_correct_m`, `crit1_m`, `crit2_m`, " +
                    "`oos`, `comm`)\n" +
                    "VALUES (?, ?, ?, ?, " +
                    "?, ?, ?, ?, ?, ?, " +
                    "?, ?, ?, ?, ?, " +
                    "?, ?, ?, ?, ?, " +
                    "?, ?, ?, ?, ?, " +
                    "?, ?);");
            statement.setInt(1, clientCriterias.getClientId());
            statement.setDate(2, Date.valueOf(clientCriterias.getDateFrom()));
            statement.setDate(3, Date.valueOf(clientCriterias.getDateTo()));
            statement.setTimestamp(4, Timestamp.valueOf(clientCriterias.getSaveDate()));

            statement.setBoolean(5, clientCriterias.isHasMz());
            statement.setBoolean(6, clientCriterias.isHasPhotoMz());
            statement.setBoolean(7, clientCriterias.isCorrectMz());
            statement.setBoolean(8, clientCriterias.isHasAddProdMz());
            statement.setBoolean(9, clientCriterias.isCrit1Mz());
            statement.setBoolean(10, clientCriterias.isCrit2Mz());

            statement.setBoolean(11, clientCriterias.isHasK());
            statement.setBoolean(12, clientCriterias.isHasPhotoK());
            statement.setBoolean(13, clientCriterias.isCorrectK());
            statement.setBoolean(14, clientCriterias.isCrit1K());
            statement.setBoolean(15, clientCriterias.isCrit2K());

            statement.setBoolean(16, clientCriterias.isHasS());
            statement.setBoolean(17, clientCriterias.isHasPhotoS());
            statement.setBoolean(18, clientCriterias.isCorrectS());
            statement.setBoolean(19, clientCriterias.isCrit1S());
            statement.setBoolean(20, clientCriterias.isCrit2S());

            statement.setBoolean(21, clientCriterias.isHasM());
            statement.setBoolean(22, clientCriterias.isHasPhotoM());
            statement.setBoolean(23, clientCriterias.isCorrectM());
            statement.setBoolean(24, clientCriterias.isCrit1M());
            statement.setBoolean(25, clientCriterias.isCrit2M());

            statement.setBoolean(26, clientCriterias.isOos());
            statement.setString(27, clientCriterias.getComment());
            statement.execute();
            statement.close();
            connection.close();
            isSucceed = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isSucceed;
    }

    public static ClientCriterias getSavedCriterias(int clientId, LocalDate dateFrom, LocalDate dateTo) {
        ClientCriterias clientCriterias = null;
        try {
            Connection connection = DataSourcePhotochecker.getDataSource().getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM `save_lka_db`\n" +
                    "WHERE `client_id` = ?\n" +
                    "AND `date_from` = ?\n" +
                    "AND `date_to` = ?\n" +
                    "ORDER BY `save_date` DESC ;");
            statement.setInt(1, clientId);
            statement.setDate(2, Date.valueOf(dateFrom));
            statement.setDate(3, Date.valueOf(dateTo));
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                clientCriterias = new ClientCriterias(clientId, dateFrom, dateTo,
                        resultSet.getTimestamp("save_date").toLocalDateTime(),

                        resultSet.getBoolean("has_mz"),
                        resultSet.getBoolean("has_photo_mz"),
                        resultSet.getBoolean("is_correct_mz"),
                        resultSet.getBoolean("has_add_prod_mz"),
                        resultSet.getBoolean("crit1_mz"),
                        resultSet.getBoolean("crit2_mz"),

                        resultSet.getBoolean("has_k"),
                        resultSet.getBoolean("has_photo_k"),
                        resultSet.getBoolean("is_correct_k"),
                        resultSet.getBoolean("crit1_k"),
                        resultSet.getBoolean("crit2_k"),

                        resultSet.getBoolean("has_s"),
                        resultSet.getBoolean("has_photo_s"),
                        resultSet.getBoolean("is_correct_s"),
                        resultSet.getBoolean("crit1_s"),
                        resultSet.getBoolean("crit2_s"),

                        resultSet.getBoolean("has_m"),
                        resultSet.getBoolean("has_photo_m"),
                        resultSet.getBoolean("is_correct_m"),
                        resultSet.getBoolean("crit1_m"),
                        resultSet.getBoolean("crit2_m"),

                        resultSet.getBoolean("oos"),
                        resultSet.getString("comm"));
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientCriterias;
    }

    public static String getLkaNameById(int id) {
        String lkaName = null;
        try {
            Connection connection = DataSourcePhotochecker.getDataSource().getConnection();
            PreparedStatement statement = connection.prepareStatement("select * from `lka_db`\n" +
                    "where `lka_id` = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                lkaName = resultSet.getString("lka_name");
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lkaName;
    }

    public static boolean writeNewLkaCriterias (List<LkaCriterias> criteriasList) {
        boolean succeed = false;
        try {
            Connection connection = DataSourcePhotochecker.getDataSource().getConnection();
            Statement statement = connection.createStatement();
            statement.execute("truncate `lka_criterias_db`");

            statement.close();

            PreparedStatement statement1 = connection.prepareStatement("INSERT INTO `lka_criterias_db`\n" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)");

            for (LkaCriterias crit : criteriasList) {
                statement1.setInt(1, crit.getLkaId());
                statement1.setString(2, crit.getCrit1Name());
                statement1.setInt(3, crit.getCrit1Mz());
                statement1.setInt(4, crit.getCrit1K());
                statement1.setInt(5, crit.getCrit1S());
                statement1.setInt(6, crit.getCrit1M());
                statement1.setString(7, crit.getCrit2Name());
                statement1.execute();
            }
            statement1.close();
            connection.close();
            succeed = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return succeed;
    }

    public static List<Responsibility> getAllResponsibilities() {
        List<Responsibility> responsibilities = new ArrayList<>();
        try {
            Connection connection = DataSourcePhotochecker.getDataSource().getConnection();
            PreparedStatement statement = connection.prepareStatement("select res.`report_type`, t.`type`, res.`distr_id`, d.`distr_name`, " +
                    "d.`region_id`, r.`region_name`, res.`resp_user`, u.`user_name`\n" +
                    "from `responsibility_db` res\n" +
                    "left join `report_type` t on res.`report_type` = t.`id`\n" +
                    "left join `distr_db` d on res.`distr_id` = d.`distr_id`\n" +
                    "left join `region_db` r on d.`region_id` = r.`region_id`\n" +
                    "left join `users` u on res.`resp_user` = u.`id`\n" +
                    "order by 1, 6, 4");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Responsibility responsibility = new Responsibility(
                        resultSet.getInt("report_type"),
                        resultSet.getString("type"),
                        resultSet.getInt("region_id"),
                        resultSet.getString("region_name"),
                        resultSet.getInt("distr_id"),
                        resultSet.getString("distr_name"),
                        resultSet.getInt("resp_user"),
                        resultSet.getString("user_name")
                        );
                responsibilities.add(responsibility);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return responsibilities;
    }

    public static Map<Integer, List<User>> getRespUsers() {
        Map<Integer, List<User>> userMap = new HashMap<>();
        try {
            Connection connection = DataSourcePhotochecker.getDataSource().getConnection();
            PreparedStatement statement = connection.prepareStatement("select ru.`user_id`, u.`user_name`\n" +
                    "from `report_type_user` ru\n" +
                    "inner join `users` u on ru.`user_id` = u.`id`\n" +
                    "where u.`user_role` = 1\n" +
                    "and ru.`report_type` = ?");

            for (int i = 1; i < 6; i++) {
                statement.setInt(1, i);
                ResultSet resultSet = statement.executeQuery();

                List<User> users = new ArrayList<>();

                while (resultSet.next()) {
                    User user = new User(resultSet.getInt("user_id"), "",
                            resultSet.getString("user_name"),
                            1, null);
                    users.add(user);
                }

                resultSet.close();
                userMap.put(i, users);
            }
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userMap;
    }

    public static boolean writeResponsibilities(List<Responsibility> respList) {
        boolean succeed = false;
        try {
            Connection connection = DataSourcePhotochecker.getDataSource().getConnection();
            PreparedStatement statement = connection.prepareStatement("UPDATE `responsibility_db`\n" +
                    "SET `resp_user` = ?\n" +
                    "WHERE `report_type` = ?\n" +
                    "AND `distr_id` = ?");

            for (Responsibility responsibility : respList) {
                statement.setInt(1, responsibility.getResponsibleId());
                statement.setInt(2, responsibility.getReportType());
                statement.setInt(3, responsibility.getDistrId());
                statement.execute();
            }

            succeed = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return succeed;
    }
}
