$(function () {
    $('#dateFrom').change(function () {
        var dateFrom = this.value;
        var dateTo = new Date(dateFrom);
        dateTo.setDate(dateTo.getDate() + 6);
        var dateToS = dateTo.getFullYear() + '-'
            + ('0' + (dateTo.getMonth() + 1)).slice(-2) + '-'
            + ('0' + dateTo.getDate()).slice(-2);
        $('#dateTo').val(dateToS);
        checkDatesAndLoadRjkam(dateFrom, dateToS);
    });

    $('#dateTo').change(function () {
        var dateFrom = $('#dateFrom').val();
        var dateTo = this.value;
        checkDatesAndLoadRjkam(dateFrom, dateTo);
    });

    $('#selRjkam').change(function () {
        var dateFrom = $('#dateFrom').val();
        var dateTo = $('#dateTo').val();
        var rjkamId = $('#selRjkam option:selected').data('value');
        clearAllFromRjkam();
        loadNka(dateFrom, dateTo, rjkamId);
        //loadCriterias(nkaId);
    });

    $('#selNka').change(function () {
        var dateFrom = $('#dateFrom').val();
        var dateTo = $('#dateTo').val();
        var rjkamId = $('#selRjkam option:selected').data('value');
        var nkaId = $('#selNka option:selected').data('value');
        clearAllFromNka();
        loadClients(dateFrom, dateTo, rjkamId, nkaId);
        loadParams(nkaId);
    });

    $('#addressTable').on('click', '.addr', function() {
        $('tr.addr').removeClass('addressSelected');
        $('#center_pane .photoBlock').remove();
        clearCriteriasPane();
        var client_id = $(this).children().eq(2).text();
        var client_type = $(this).children().eq(3).text();
        $('#clientId').text('ID: ' + client_id);
        $('#clientType').text('Тип: ' + client_type);
        var dateFrom = $('#dateFrom').val();
        var dateTo = $("#dateTo").val();
        $(this).addClass('addressSelected');
        loadPhotos(dateFrom, dateTo, client_id);
        if ($(this).hasClass('addressChecked')) {
            loadSavedCriterias(client_id, dateFrom, dateTo);
        }
    });

    $('#content_pane').on('dblclick', '.photoBlock img', function (data) {
        var num = $(this).data('num');
        var url = $(this).attr('src');
        var date = $(this).parent().find('.photoDate').text();
        var dateAdd = $(this).parent().find('.addDate').text();
        var comment = $(this).parent().find('textarea').val();
        date = date.replace("Дата: ", "");
        dateAdd = dateAdd.replace("Дата добавления: ", "");
        var count = $('#center_pane .photoBlock:last-child img').data('num');
        $('#showPhoto').css('display', 'block');
        $('#fullPhotoCont img').attr('src', url);
        $('#fullPhotoCont img').attr('data-zoom-image', url);
        $('#fullPhotoNum').text(num + " /");
        $('#fullPhotoCount').text(count);
        $('#fullPhotoDate').text(date);
        $('#fullPhotoAddDate').text(dateAdd);
        $('#fullPhotoComment').text(comment);
    });

    $('#zoomIn').on('click', function () {
        var width = $('#fullPhotoCont img').css('max-width');
        var height = $('#fullPhotoCont img').css('max-height');
        var newWidth = width.substring(0, width.length - 1) * 1.1 + "%";
        var newHeight = height.substring(0, height.length - 1) * 1.1 + "%";
        $('#fullPhotoCont img').css('max-width', newWidth);
        $('#fullPhotoCont img').css('max-height', newHeight);
    });

    $('#zoomOut').on('click', function () {
        var width = $('#fullPhotoCont img').css('max-width');
        var height = $('#fullPhotoCont img').css('max-height');
        var newWidth = width.substring(0, width.length - 1) / 1.1 + "%";
        var newHeight = height.substring(0, height.length - 1) / 1.1 + "%";
        $('#fullPhotoCont img').css('max-width', newWidth);
        $('#fullPhotoCont img').css('max-height', newHeight);
    });

    $('#close').on('click', function () {
        $('#showPhoto').css('display', 'none');
    });

    $('#toLeft').on('click', function () {
        var numFull = $('#fullPhotoNum').text();
        var num = numFull.substring(0, numFull.length - 2);
        if (num === 1 || num === '1') return;
        var newNum = num - 1;
        fillDatasOnPhotoShow(newNum);
    });

    $('#toRight').on('click', function () {
        var numFull = $('#fullPhotoNum').text();
        var num = numFull.substring(0, numFull.length - 2);
        var count = $('#fullPhotoCount').text();
        if (num === count) return;
        var newNum = num * 1 + 1;
        fillDatasOnPhotoShow(newNum);
    });

    $('#turnForward').on('click', function () {
        $('#fullPhotoCont img').removeClass('rotateLeft');
        $('#fullPhotoCont img').toggleClass('rotateRight');
        $('#fullPhotoCont img').css('max-width', '100%');
        $('#fullPhotoCont img').css('max-height', '100%');
    });

    $('#turnBack').on('click', function () {
        $('#fullPhotoCont img').removeClass('rotateRight');
        $('#fullPhotoCont img').toggleClass('rotateLeft')
        $('#fullPhotoCont img').css('max-width', '100%');
        $('#fullPhotoCont img').css('max-height', '100%');
    });

    $('#saveButton').on('click', function () {
        var addrRow = $('#addressTable tr.addressSelected');
        var clientId = addrRow.children().eq(2).text();
        saveCriteriasByClient(clientId);
        addrRow.addClass('addressChecked');
    });

    $('#clearButton').on('click', function () {
        clearCriteriasPane();
    });

    $('#to_xlsx').on('click', function (e) {
        e.preventDefault();
        window.location.href = 'nka/getExcelReport?dateFrom=' + $('#dateFrom').val() +
                '&dateTo=' + $('#dateTo').val();
    });

    $('#nka_param_cancel').on('click', function () {
        location.reload();
    });

    $('#nka_param_save').on('click', function () {
        saveAllCriterias();
    });
});

function checkDatesAndLoadRjkam(dateFrom, dateTo) {
    clearAllFromDates();
    loadRjkam(dateFrom, dateTo);
}

function clearAllFromDates() {
    $('#selRjkam option').remove();
    clearAllFromRjkam();
}

function clearAllFromRjkam() {
    $('#selNka option').remove();
    clearAllFromNka();
}

function clearAllFromNka() {
    clearNkaParams();
    $('#addressTable tr').remove();
    $('#center_pane .photoBlock').remove();
    $('#clientId').text("ID: ");
    $('#clientType').text("Тип: ");
    clearCriteriasPane();
}

function clearNkaParams() {
    $('#mzDPLabel').text('Доля полки');
    $('#mzBBLabel').text('Бренд-блок');
    $('#mzMRLabel').text('Место размещения');

    $('#kDPLabel').text('Доля полки');
    $('#kBBLabel').text('Бренд-блок');
    $('#kMRLabel').text('Место размещения');

    $('#sDPLabel').text('Доля полки');
    $('#sBBLabel').text('Бренд-блок');
    $('#sMRLabel').text('Место размещения');
}

function clearCriteriasPane() {
    $('#mzDP').prop('checked', false);
    $('#mzBB').prop('checked', false);
    $('#mzMR').prop('checked', false);
    $('#mzComment').val('');

    $('#kDP').prop('checked', false);
    $('#kBB').prop('checked', false);
    $('#kMR').prop('checked', false);
    $('#kComment').val('');

    $('#sDP').prop('checked', false);
    $('#sBB').prop('checked', false);
    $('#sMR').prop('checked', false);
    $('#sComment').val('');

    $('#mzDouble').prop('checked', false);
    $('#kDouble').prop('checked', false);
    $('#sDouble').prop('checked', false);

    $('#mzDmA').prop('checked', false);
    $('#kDmA').prop('checked', false);
    $('#sDmA').prop('checked', false);

    $('#mzDmNa').prop('checked', false);
    $('#kDmNa').prop('checked', false);
    $('#sDmNa').prop('checked', false);
}

function loadRjkam(dateFrom, dateTo) {
    $('.datePicker').css('background-color', '');
    $('#loader').css('display', 'block');
    $.post('nka/getRjkam',
        {
            dateFrom: dateFrom,
            dateTo: dateTo
        })
        .done (function (data) {
            checkForRedirect(data);
            $('#selRjkam').append(data);
        })
        .fail (function() {
            showErrorPane();
        })
        .always (function (data) {
            $('#loader').css('display', 'none');
        });
}

function loadNka(dateFrom, dateTo, rjkamId) {
    $('.datePicker').css('background-color', '');
    $('#loader').css('display', 'block');
    $.post('nka/getNka',
        {
            dateFrom: dateFrom,
            dateTo: dateTo,
            rjkamId: rjkamId
        })
        .done (function (data) {
            checkForRedirect(data);
            $('#selNka').append(data);
        })
        .fail (function() {
            showErrorPane();
        })
        .always (function (data) {
            $('#loader').css('display', 'none');
        });
}

function loadParams(nkaId) {
    $.post('nka/getParams',
        {
            nkaId: nkaId
        })
        .done(function (data) {
            checkForRedirect(data);

            if (data.mzDpShort != '') {
                $('#mzDPLabel').append(" (<abbr title='" + data.mzDpFull + "'>" + data.mzDpShort + "</abbr>)");
            } else {
                $('#mzDPLabel').text("");
                $('#mzDPLabel').append("<s>Доля полки</s>");
            }

            if (data.mzBbShort != '') {
                $('#mzBBLabel').append(" (<abbr title='" + data.mzBbFull + "'>" + data.mzBbShort + "</abbr>)");
            } else {
                $('#mzBBLabel').text("");
                $('#mzBBLabel').append("<s>Бренд-блок</s>");
            }

            if (data.mzMrShort != '') {
                $('#mzMRLabel').append(" (<abbr title='" + data.mzMrFull + "'>" + data.mzMrShort + "</abbr>)");
            } else {
                $('#mzMRLabel').text("");
                $('#mzMRLabel').append("<s>Место размещения</s>");
            }

            if (data.kDpShort != '') {
                $('#kDPLabel').append(" (<abbr title='" + data.kDpFull + "'>" + data.kDpShort + "</abbr>)");
            } else {
                $('#kDPLabel').text("");
                $('#kDPLabel').append("<s>Доля полки</s>");
            }

            if (data.kBbShort != '') {
                $('#kBBLabel').append(" (<abbr title='" + data.kBbFull + "'>" + data.kBbShort + "</abbr>)");
            } else {
                $('#kBBLabel').text("");
                $('#kBBLabel').append("<s>Бренд-блок</s>");
            }

            if (data.kMrShort != '') {
                $('#kMRLabel').append(" (<abbr title='" + data.kMrFull + "'>" + data.kMrShort + "</abbr>)");
            } else {
                $('#kMRLabel').text("");
                $('#kMRLabel').append("<s>Место размещения</s>");
            }

            if (data.sDpShort != '') {
                $('#sDPLabel').append(" (<abbr title='" + data.sDpFull + "'>" + data.sDpShort + "</abbr>)");
            } else {
                $('#sDPLabel').text("");
                $('#sDPLabel').append("<s>Доля полки</s>");
            }

            if (data.sBbShort != '') {
                $('#sBBLabel').append(" (<abbr title='" + data.sBbFull + "'>" + data.sBbShort + "</abbr>)");
            } else {
                $('#sBBLabel').text("");
                $('#sBBLabel').append("<s>Бренд-блок</s>");
            }

            if (data.sMrShort != '') {
                $('#sMRLabel').append(" (<abbr title='" + data.sMrFull + "'>" + data.sMrShort + "</abbr>)");
            } else {
                $('#sMRLabel').text("");
                $('#sMRLabel').append("<s>Место размещения</s>");
            }
        })
        .fail(function () {
            showErrorPane();
        })
}

function loadClients(dateFrom, dateTo, rjkamId, nkaId) {
    $('#loader').css('display', 'block');
    $.post('nka/getClients',
        {
            dateFrom: dateFrom,
            dateTo: dateTo,
            rjkamId: rjkamId,
            nkaId: nkaId
        })
        .done(function (data) {
            checkForRedirect(data);
            $('#addressTable').append(data);
        })
        .fail(function() {
            showErrorPane()
        })
        .always(function (data) {
            $('#loader').css('display', 'none');
        })
}

function loadPhotos(dateFrom, dateTo, clientId) {
    $('#loader').css('display', 'block');
    $.post('nka/getPhotos',
        {
            dateFrom: dateFrom,
            dateTo: dateTo,
            clientId: clientId
        })
        .done(function (data) {
            checkForRedirect(data);
            $('#center_pane').append(data);
        })
        .fail(function() {
            showErrorPane()
        })
        .always(function (data) {
            $('#loader').css('display', 'none');
        })
}

function loadSavedCriterias(clientId, dateFrom, dateTo) {
    $.post('nka/getSavedCriterias',
        {
            clientId: clientId,
            dateFrom: dateFrom,
            dateTo: dateTo
        })
        .done(function (data) {
            checkForRedirect(data);
            $('#mzDP').prop('checked', data.mzDP);
            $('#mzBB').prop('checked', data.mzBB);
            $('#mzMR').prop('checked', data.mzMR);
            $('#mzComment').val(data.mzComment);

            $('#kDP').prop('checked', data.kDP);
            $('#kBB').prop('checked', data.kBB);
            $('#kMR').prop('checked', data.kMR);
            $('#kComment').val(data.kComment);

            $('#sDP').prop('checked', data.sDP);
            $('#sBB').prop('checked', data.sBB);
            $('#sMR').prop('checked', data.sMR);
            $('#sComment').val(data.sComment);

            $('#mzDouble').prop('checked', data.mzDouble);
            $('#kDouble').prop('checked', data.kDouble);
            $('#sDouble').prop('checked', data.sDouble);

            $('#mzDmA').prop('checked', data.mzDmA);
            $('#kDmA').prop('checked', data.kDmA);
            $('#sDmA').prop('checked', data.sDmA);

            $('#mzDmNa').prop('checked', data.mzDmNa);
            $('#kDmNa').prop('checked', data.kDmNa);
            $('#sDmNa').prop('checked', data.sDmNa);
        })
        .fail(function () {
            showErrorPane();
        })
}

function fillDatasOnPhotoShow(newPhotoBlockNum) {
    var newUrl = $('#content_pane img[data-num=' + newPhotoBlockNum + ']').attr('src');
    var newPhotoBlock = $('#content_pane img[data-num=' + newPhotoBlockNum + ']').parent();
    var date = newPhotoBlock.find('.photoDate').text();
    var dateAdd = newPhotoBlock.find('.addDate').text();
    var comment = newPhotoBlock.find('textarea').val();
    date = date.replace("Дата: ", "");
    dateAdd = dateAdd.replace("Дата добавления: ", "");
    $('#fullPhotoDate').text(date);
    $('#fullPhotoAddDate').text(dateAdd);
    $('#fullPhotoComment').text(comment);
    $('#fullPhotoCont img').attr('src', newUrl);
    $('#fullPhotoNum').text(newPhotoBlockNum + " /");
    $('#fullPhotoCont img').css('max-width', '100%');
    $('#fullPhotoCont img').css('max-height', '100%');
    $('#fullPhotoCont img').removeClass('rotateLeft');
    $('#fullPhotoCont img').removeClass('rotateRight');
}

function saveCriteriasByClient(clientId) {
    $('#loader').css('display', 'block');
    var crit = {
        clientId: clientId,

        mzDP: $('#mzDP').is(':checked'),
        mzBB: $('#mzBB').is(':checked'),
        mzMR: $('#mzMR').is(':checked'),
        mzComment: $('#mzComment').val(),

        kDP: $('#kDP').is(':checked'),
        kBB: $('#kBB').is(':checked'),
        kMR: $('#kMR').is(':checked'),
        kComment: $('#kComment').val(),

        sDP: $('#sDP').is(':checked'),
        sBB: $('#sBB').is(':checked'),
        sMR: $('#sMR').is(':checked'),
        sComment: $('#sComment').val(),

        mzDouble: $('#mzDouble').is(':checked'),
        kDouble: $('#kDouble').is(':checked'),
        sDouble: $('#sDouble').is(':checked'),

        mzDmA: $('#mzDmA').is(':checked'),
        kDmA: $('#kDmA').is(':checked'),
        sDmA: $('#sDmA').is(':checked'),

        mzDmNa: $('#mzDmNa').is(':checked'),
        kDmNa: $('#kDmNa').is(':checked'),
        sDmNa: $('#sDmNa').is(':checked')
    }

    $.post('nka/saveCriterias',
        {
            crit: JSON.stringify(crit),
            dateFrom: $('#dateFrom').val(),
            dateTo: $('#dateTo').val()
        })
        .done(function (data) {
            checkForRedirect(data);
            showSavedSuccessfullyPane();
        })
        .fail(function (data) {
            showErrorPane();
        })
        .always(function (data) {
            $('#loader').css('display', 'none');
        })
}

function saveAllCriterias() {
    var criteriasArray = new Array();
    var nkaParamList = $('tbody');

    $('#loader').css('display', 'block');

    for (var i = 0; i < nkaParamList.length; i++) {
        var critElem = {
            nkaId: nkaParamList.eq(i).find('.nkaId').text(),
            nkaName: nkaParamList.eq(i).find('.nkaName').text(),
            mzDpShort: nkaParamList.eq(i).find('.mzDpShortInput').val(),
            mzDpFull: nkaParamList.eq(i).find('.mzDpFullInput').val(),
            mzBbShort: nkaParamList.eq(i).find('.mzBbShortInput').val(),
            mzBbFull: nkaParamList.eq(i).find('.mzBbFullInput').val(),
            mzMrShort: nkaParamList.eq(i).find('.mzMrShortInput').val(),
            mzMrFull: nkaParamList.eq(i).find('.mzMrFullInput').val(),
            kDpShort: nkaParamList.eq(i).find('.kDpShortInput').val(),
            kDpFull: nkaParamList.eq(i).find('.kDpFullInput').val(),
            kBbShort: nkaParamList.eq(i).find('.kBbShortInput').val(),
            kBbFull: nkaParamList.eq(i).find('.kBbFullInput').val(),
            kMrShort: nkaParamList.eq(i).find('.kMrShortInput').val(),
            kMrFull: nkaParamList.eq(i).find('.kMrFullInput').val(),
            sDpShort: nkaParamList.eq(i).find('.sDpShortInput').val(),
            sDpFull: nkaParamList.eq(i).find('.sDpFullInput').val(),
            sBbShort: nkaParamList.eq(i).find('.sBbShortInput').val(),
            sBbFull: nkaParamList.eq(i).find('.sBbFullInput').val(),
            sMrShort: nkaParamList.eq(i).find('.sMrShortInput').val(),
            sMrFull: nkaParamList.eq(i).find('.sMrFullInput').val(),
        };
        criteriasArray.push(critElem);
    };
    $.post('nka/saveNewParams',
        {
            paramList: JSON.stringify(criteriasArray)
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