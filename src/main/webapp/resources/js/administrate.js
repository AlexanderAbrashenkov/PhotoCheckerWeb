$(function () {
    $('#criteria_editor_pane').on('change', '.lka_id', function () {
        var lka_id = $(this).val();
        getLkaNameById(lka_id, $(this).parent().children('.lka_name'));
    });

    $('#criteria_editor_pane').on('click', '.remove_row', function () {
        $(this).parent().remove();
    });

    $('#criteria_editor_pane').on('click', '.add_row', function () {
        $(this).before('<div class="lka_criteria">' +
            '<input type="text" class="crit lka_name" value="" readonly>' +
            '<input type="text" class="crit lka_id" value="">' +
            '<input type="text" class="crit crit_name crit1_name" value="Доля полки">' +
            '<input type="text" class="crit crit_value crit1_mz" value="">' +
            '<div class="crit perc">%</div>' +
            '<input type="text" class="crit crit_value crit1_k" value="">' +
            '<div class="crit perc">%</div>' +
            '<input type="text" class="crit crit_value crit1_s" value="">' +
            '<div class="crit perc">%</div>' +
            '<input type="text" class="crit crit_value crit1_m" value="">' +
            '<div class="crit perc">%</div>' +
            '<input type="text" class="crit crit_name crit2_name" value="Бренд-блок">' +
            '<img class="remove_row" src="/resources/images/cancel.png">' +
            '</div>');
    });

    $('#lka_crit_cancel').on('click', function () {
        location.reload();
    });

    $('#lka_crit_save').on('click', function () {
        saveAllCriterias();
    });

    $('#resp_save').on('click', function () {
        saveResponsibilities();
    });

    $('#nka_resp_save').on('click', function () {
        saveNkaResp();
    });

    $('#nst_resp_save').on('click', function () {
        saveNstResp();
    });

    $('#resp_cancel').on('click', function () {
        location.reload();
    });

    $('#crus_container').on('change', '#login', function () {
        var login = $(this).val();
        checkUserExist(login);
    });

    $('#crus_container').on('change', '#password', function () {
        arePasswordsSame();
    });

    $('#crus_container').on('change', '#repeat_password', function () {
        arePasswordsSame();
    });

    $('#crus_save').on('click', function () {
        saveNewUser();
    });
});

function getLkaNameById(lka_id, element) {
    $('#loader').css('display', 'block');
    $.post('lka/getLkaNameById',
        {
            lkaId: lka_id
        })
        .done(function (data) {
            checkForRedirect(data);
            element.val(data.lkaName);
            console.log(data);
        })
        .fail(function () {
            showErrorPane();
        })
        .always(function () {
            $('#loader').css('display', 'none');
        });
}

function saveAllCriterias() {
    var criteriasArray = new Array();
    var lkaCritList = $('.lka_criteria');
    var canContinue = true;
    for (var i = 0; i < lkaCritList.length; i++) {
        lkaCritList.eq(i).children('.lka_id').css('background-color', '');
        if (!$.isNumeric(lkaCritList.eq(i).children('.lka_id').val())) {
            lkaCritList.eq(i).children('.lka_id').css('background-color', '#ff9dae');
            canContinue = false;
        }
        if (!$.isNumeric(lkaCritList.eq(i).children('.crit1_mz').val()) || lkaCritList.eq(i).children('.crit1_mz').val() > 100) {
            lkaCritList.eq(i).children('.crit1_mz').css('background-color', '#ff9dae');
            canContinue = false;
        }
        if (!$.isNumeric(lkaCritList.eq(i).children('.crit1_k').val()) || lkaCritList.eq(i).children('.crit1_k').val() > 100) {
            lkaCritList.eq(i).children('.crit1_k').css('background-color', '#ff9dae');
            canContinue = false;
        }
        if (!$.isNumeric(lkaCritList.eq(i).children('.crit1_s').val()) || lkaCritList.eq(i).children('.crit1_s').val() > 100) {
            lkaCritList.eq(i).children('.crit1_s').css('background-color', '#ff9dae');
            canContinue = false;
        }
        if (!$.isNumeric(lkaCritList.eq(i).children('.crit1_m').val()) || lkaCritList.eq(i).children('.crit1_m').val() > 100) {
            lkaCritList.eq(i).children('.crit1_m').css('background-color', '#ff9dae');
            canContinue = false;
        }
    }

    if (!canContinue) return;

    $('#loader').css('display', 'block');

    for (var i = 0; i < lkaCritList.length; i++) {
        var critElem = {
            lka: {
                name: lkaCritList.eq(i).children('.lka_name').val(),
                id: lkaCritList.eq(i).children('.lka_id').val()
            },
            crit1Name: lkaCritList.eq(i).children('.crit1_name').val(),
            crit1Mz: lkaCritList.eq(i).children('.crit1_mz').val(),
            crit1K: lkaCritList.eq(i).children('.crit1_k').val(),
            crit1S: lkaCritList.eq(i).children('.crit1_s').val(),
            crit1M: lkaCritList.eq(i).children('.crit1_m').val(),
            crit2Name: lkaCritList.eq(i).children('.crit2_name').val()
        };
        criteriasArray.push(critElem);
    };
    $.post('lka/saveNewCriterias',
        {
            critList: JSON.stringify(criteriasArray)
        })
        .done(function (data) {
            checkForRedirect(data);
            console.log(data);
            if (data.answer === true) {
                showSavedSuccessfullyPane();
            } else {
                showErrorPane();
            }
        })
        .fail(function (data) {
            showErrorPane();
        })
        .always(function () {
            $('#loader').css('display', 'none');
        })
}

function saveResponsibilities() {
    var respArray = new Array();
    var respList = $('.responsib');
    var canContinue = true;

    $('#loader').css('display', 'block');

    for (var i = 0; i < respList.length; i++) {
        var respElem = {
            reportType: {
                id: respList.eq(i).children('.type_name').attr('name')
            },
            distr: {
                id: respList.eq(i).children('.distr_name').attr('name')
            },
            user: {
                id: respList.eq(i).children('.resp_name').children('option').filter(':selected').attr('name')
            }
        };
        respArray.push(respElem);
    };
    $.post('responsib/save',
        {
            respList: JSON.stringify(respArray)
        })
        .done(function (data) {
            checkForRedirect(data);
            console.log(data);
            if (data.answer === true) {
                showSavedSuccessfullyPane();
            } else {
                showErrorPane();
            }
        })
        .fail(function (data) {
            showErrorPane();
        })
        .always(function () {
            $('#loader').css('display', 'none');
        })
}

function saveNkaResp() {
    var respArray = new Array();
    var respList = $('.responsib');
    var canContinue = true;

    $('#loader').css('display', 'block');

    for (var i = 0; i < respList.length; i++) {
        var respElem = {
            nkaType: {
                id: respList.eq(i).children('.type_name').attr('name')
            },
            distr: {
                id: respList.eq(i).children('.distr_name').attr('name')
            },
            user: {
                id: respList.eq(i).children('.resp_name').children('option').filter(':selected').attr('name')
            }
        };
        respArray.push(respElem);
    };
    $.post('mlkaResp/save',
        {
            nkaRespList: JSON.stringify(respArray)
        })
        .done(function (data) {
            checkForRedirect(data);
            console.log(data);
            if (data.answer === true) {
                showSavedSuccessfullyPane();
            } else {
                showErrorPane();
            }
        })
        .fail(function (data) {
            showErrorPane();
        })
        .always(function () {
            $('#loader').css('display', 'none');
        })
}

function saveNstResp() {
    var respArray = new Array();
    var respList = $('.responsib');
    var canContinue = true;

    $('#loader').css('display', 'block');

    for (var i = 0; i < respList.length; i++) {
        var respElem = {
            nstFormat: {
                id: respList.eq(i).children('.nstFormat_name').attr('name')
            },
            nstObl: {
                id: respList.eq(i).children('.nstObl_name').attr('name')
            },
            user: {
                id: respList.eq(i).children('.resp_name').children('option').filter(':selected').attr('name')
            }
        };
        respArray.push(respElem);
    };
    $.post('nstResp/save',
        {
            nstRespList: JSON.stringify(respArray)
        })
        .done(function (data) {
            checkForRedirect(data);
            console.log(data);
            if (data.answer === true) {
                showSavedSuccessfullyPane();
            } else {
                showErrorPane();
            }
        })
        .fail(function (data) {
            showErrorPane();
        })
        .always(function () {
            $('#loader').css('display', 'none');
        })
}

function checkUserExist(login) {
    if (login === undefined || login === "") {
        $('#loginError').css('display', 'none');
        $('#loginSuccess').css('display', 'none');
        return;
    }

    $('#loader').css('display', 'block');

    $.post('create_user/check_login',
        {
            login: login
        })
        .done(function (data) {
            checkForRedirect(data);
            console.log(data);
            if (data.answer === true) {
                $('#loginError').css('display', 'none');
                $('#loginSuccess').css('display', 'block');
            } else {
                $('#loginError').css('display', 'block');
                $('#loginSuccess').css('display', 'none');
            }
        })
        .fail(function (data) {
            showErrorPane();
        })
        .always(function () {
            $('#loader').css('display', 'none');
        })
}

function arePasswordsSame() {
    var pass = $('#password').val();
    var pass2 = $('#repeat_password').val();

    if (pass === undefined || pass2 === undefined || pass === "" || pass2 === "") {
        $('#passError').css('display', 'none');
        $('#passSuccess').css('display', 'none');
        return;
    }

    if (pass === pass2) {
        $('#passError').css('display', 'none');
        $('#passSuccess').css('display', 'block');
    } else {
        $('#passError').css('display', 'block');
        $('#passSuccess').css('display', 'none');
    }
}

function saveNewUser() {
    if ($('#loginError').css('display') === 'block' ||
            $('#passError').css('display') === 'block' ||
            $('#user_name').val() === "" || $('#user_name').val() === undefined) {
        return;
    }

    $('#loader').css('display', 'block');

    var login = $('#login').val();
    var password = $('#password').val();
    var fio = $('#user_name').val();
    var role = $('#role').children('option').filter(':selected').attr('name');
    var report_types = [];

    for (var i = 0; i < $('#report_types').children('input').length; i++) {
        var elem = $('#report_types').children('input').eq(i);
        if (elem.prop('checked')) {
            report_types.push(elem.attr('name'));
        }
    }

    $.post('create_user/add_user',
        {
            login: login,
            password: password,
            fio: fio,
            role: role,
            report_types: JSON.stringify(report_types)
        })
        .done(function (data) {
            checkForRedirect(data);
            console.log(data);
            if (data.answer === true) {
                showSavedSuccessfullyPane();
            } else {
                showErrorPane();
            }
        })
        .fail(function (data) {
            showErrorPane();
        })
        .always(function () {
            $('#loader').css('display', 'none');
        })
}

function clearUploadInfoBlock() {
    $('#upload_info_block').text('');
}