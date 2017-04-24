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
            '<img class="remove_row" src="../images/cancel.png">' +
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

    $('#resp_cancel').on('click', function () {
        location.reload();
    });
});

function getLkaNameById(lka_id, element) {
    $('#loader').css('display', 'block');
    $.post('lka/getLkaNameById',
        {
            lkaId: lka_id
        })
        .done(function (data) {
            checkForRedirect(data)
            element.val(data.lkaName);
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
            lkaName: lkaCritList.eq(i).children('.lka_name').val(),
            lkaId: lkaCritList.eq(i).children('.lka_id').val(),
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
            reportType: respList.eq(i).children('.type_name').attr('name'),
            distrId: respList.eq(i).children('.distr_name').attr('name'),
            responsibleName: respList.eq(i).children('.resp_name').children('option').filter(':selected').text()
        };
        respArray.push(respElem);
    };
    $.post('saveResponsibs',
        {
            respList: JSON.stringify(respArray)
        })
        .done(function (data) {
            checkForRedirect(data);
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