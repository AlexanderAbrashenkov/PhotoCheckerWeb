package com.photochecker.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by market6 on 11.04.2017.
 */
public class Upload {

    private static Connection connection;

    public static int uploadDataProcess(BufferedReader reader, String date) {
        LocalDate dateAdd = LocalDate.parse(date);
        int recordCounter = 0;
        try {
            connection = DataSourcePhotochecker.getDataSource().getConnection();

            Set<Integer> regionSet = getRegionsSet();
            Set<Integer> distrSet = getDistrSet();
            Set<Integer> lkaSet = getLkaSet();
            Set<Integer> clientSet = getClientSet();
            Set<String> photoSet = getPhotoSet(dateAdd);
            Set<Double> responsibilitySet = getResponsibilitySet();

            PreparedStatement statementModifyClient = connection.prepareStatement("UPDATE `client_card`\n" +
                    "SET `client_name` = ?, `client_address` = ?, `channel_id` = ?, `lka_id` = ?, `type_name` = ?\n" +
                    "WHERE `client_id` = ?");

            PreparedStatement statementRegion = connection.prepareStatement("INSERT INTO `region_db` VALUES (?, ?);");
            PreparedStatement statementDistr = connection.prepareStatement("INSERT INTO `distr_db` VALUES (?, ?, ?);");
            PreparedStatement statementLka = connection.prepareStatement("INSERT INTO `lka_db` VALUES (?, ?);");
            PreparedStatement statementClient = connection.prepareStatement("INSERT INTO `client_card`\n" +
                    "(`client_id`, `client_name`, `client_address`, `region_id`, `obl`, `distributor_id`, `channel_id`, `lka_id`, `type_name`)\n" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);");
            PreparedStatement statementPhoto = connection.prepareStatement("INSERT INTO `photo_card`\n" +
                    "(`client_id`, `url`, `date`, `date_add`, `comment`, `report_type`)\n" +
                    "VALUES (?, ?, ?, ?, ?, ?);");
            PreparedStatement statementResponsibility = connection.prepareStatement("INSERT INTO `responsibility_db` " +
                    "(`report_type`, `distr_id`) VALUES (?, ?);");

            while (reader.ready()) {
                String record = reader.readLine();

                //todo: для разных каналов разделить
                if (record.startsWith("--")) continue;

                String[] recordParts = record.split("; ");

                if (recordParts.length < 16) continue;

                int region_id = Integer.parseInt(recordParts[0]);
                if (!regionSet.contains(region_id)) {
                    statementRegion.setInt(1, region_id);
                    statementRegion.setString(2, recordParts[1]);
                    statementRegion.execute();
                    regionSet.add(region_id);
                }

                int distr_id = Integer.parseInt(recordParts[4]);
                if (!distrSet.contains(distr_id)) {
                    statementDistr.setInt(1, distr_id);
                    statementDistr.setString(2, recordParts[3]);
                    statementDistr.setInt(3, region_id);
                    statementDistr.execute();
                    distrSet.add(distr_id);
                }

                int lka_id = Integer.parseInt(recordParts[6]);
                if (lka_id != 0) {
                    if (!lkaSet.contains(lka_id)) {
                        statementLka.setInt(1, lka_id);
                        statementLka.setString(2, recordParts[5]);
                        statementLka.execute();
                        lkaSet.add(lka_id);
                    }
                }

                //todo: add report type
                double resp_pair = Double.parseDouble(5 + "." + distr_id);
                if (!responsibilitySet.contains(resp_pair)) {
                    //todo: enter report_type
                    statementResponsibility.setInt(1, 5);
                    statementResponsibility.setInt(2, distr_id);
                    statementResponsibility.execute();
                    responsibilitySet.add(resp_pair);
                }

                int client_id = Integer.parseInt(recordParts[7]);
                if (!clientSet.contains(client_id)) {
                    statementClient.setInt(1, client_id);
                    statementClient.setString(2, recordParts[8]);
                    statementClient.setString(3, recordParts[9]);
                    statementClient.setInt(4, region_id);
                    statementClient.setString(5, recordParts[2]);
                    statementClient.setInt(6, distr_id);
                    statementClient.setInt(7, Integer.parseInt(recordParts[10]));
                    statementClient.setInt(8, lka_id);
                    statementClient.setString(9, recordParts[14]);
                    statementClient.execute();
                    clientSet.add(client_id);
                } else {
                    statementModifyClient.setString(1, recordParts[8]);
                    statementModifyClient.setString(2, recordParts[9]);
                    statementModifyClient.setInt(3, Integer.parseInt(recordParts[10]));
                    statementModifyClient.setInt(4, lka_id);
                    statementModifyClient.setString(5, recordParts[14]);
                    statementModifyClient.setInt(6, client_id);
                    statementModifyClient.execute();
                }

                Timestamp addDate = Timestamp.valueOf(recordParts[12]);
                LocalDateTime addDateLocal = addDate.toLocalDateTime();
                String shortUrl = recordParts[13];
                String day = Integer.toString(addDateLocal.getDayOfMonth()).length() == 1 ? "0" + addDateLocal.getDayOfMonth() : Integer.toString(addDateLocal.getDayOfMonth());
                String month = Integer.toString(addDateLocal.getMonthValue()).length() == 1 ? "0" + addDateLocal.getMonthValue() : Integer.toString(addDateLocal.getMonthValue());
                String fullUrl = "https://report.ncsd.ru/upload/foto100g3/" + addDateLocal.getYear() + "_" + month +
                        "/" + day + "/" + shortUrl;

                if (!photoSet.contains(fullUrl)) {
                    statementPhoto.setInt(1, client_id);
                    statementPhoto.setString(2, fullUrl);
                    statementPhoto.setTimestamp(3, Timestamp.valueOf(recordParts[11]));
                    statementPhoto.setTimestamp(4, addDate);
                    statementPhoto.setString(5, recordParts[15]);
                    statementPhoto.setInt(6, 5);
                    statementPhoto.execute();
                    photoSet.add(fullUrl);
                }
                recordCounter++;
            }

            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            closeConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return recordCounter;
    }

    private static Set<Integer> getRegionsSet() {
        Set<Integer> regionSet = new HashSet<Integer>();

        try {
            Statement statement =  connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT `region_id` FROM `region_db`");
            while (resultSet.next()) {
                regionSet.add(resultSet.getInt("region_id"));
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return regionSet;
    }

    private static Set<Integer> getDistrSet() {
        Set<Integer> distrSet = new HashSet<Integer>();

        try {
            Statement statement =  connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT `distr_id` FROM `distr_db`");
            while (resultSet.next()) {
                distrSet.add(resultSet.getInt("distr_id"));
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return distrSet;
    }

    private static Set<Integer> getLkaSet() {
        Set<Integer> lkaSet = new HashSet<Integer>();

        try {
            Statement statement =  connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT `lka_id` FROM `lka_db`");
            while (resultSet.next()) {
                lkaSet.add(resultSet.getInt("lka_id"));
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lkaSet;
    }

    private static Set<Integer> getClientSet() {
        Set<Integer> clientSet = new HashSet<Integer>();

        try {
            Statement statement =  connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT `client_id` FROM `client_card`");
            while (resultSet.next()) {
                clientSet.add(resultSet.getInt("client_id"));
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clientSet;
    }

    private static Set<String> getPhotoSet(LocalDate dateAdd) {
        Set<String> photoSet = new HashSet<String>();

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT `url` FROM `photo_card`\n" +
                    "WHERE `date_add` >= ? and `date_add` < ?");
            statement.setDate(1, Date.valueOf(dateAdd));
            statement.setDate(2, Date.valueOf(dateAdd.plusDays(1)));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                photoSet.add(resultSet.getString("url"));
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return photoSet;
    }

    private static Set<Double> getResponsibilitySet() {
        Set<Double> responsibilitySet = new HashSet<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT `report_type`, `distr_id` FROM `responsibility_db`");
            while (resultSet.next()) {
                Double resp = Double.parseDouble(resultSet.getInt("report_type") + "." +
                    resultSet.getInt("distr_id"));
                responsibilitySet.add(resp);
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return responsibilitySet;
    }

    private static void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
