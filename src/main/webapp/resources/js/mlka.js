$(function () {
    $('#dateFrom').change(function () {
        var dateFrom = this.value;
        $('#dateTo').val(dateFrom);
        var dateDiff = 0;
        clearAllFromDates();
        loadNka(dateFrom, dateFrom);
        $('#saveButton').removeClass('hidden');
    });

    $('#dateTo').change(function () {
        var dateFrom = $('#dateFrom').val();
        var dateTo = this.value;
        var dateDiffer = dateDiff(dateFrom, dateTo);
        console.log(dateFrom);
        console.log(dateTo);
        console.log(dateDiffer);
        clearAllFromDates();
        if (dateTo >= dateFrom) {
            $('.datePicker').css('background-color', 'red');
            loadNka(dateFrom, dateTo);
        } else {
            $('.datePicker').css('background-color', 'red');
        }
        if (dateDiffer === 0) {
            $('#saveButton').removeClass('hidden');
        } else {
            $('#saveButton').addClass('hidden');
        }
    });

    $('#selNka').change(function () {
        var dateFrom = $('#dateFrom').val();
        var dateTo = $('#dateTo').val();
        var nkaId = $('#selNka option:selected').data('value');
        clearAllFromNka();
        loadRegions(dateFrom, dateTo, nkaId);
        loadCriterias(nkaId);
    });

    $('#selRegion').change(function () {
        var dateFrom = $('#dateFrom').val();
        var dateTo = $('#dateTo').val();
        var nkaId = $('#selNka option:selected').data('value');
        var regionId = $('#selRegion option:selected').data('value');
        clearAllFromRegion();
        loadDistrs(dateFrom, dateTo, nkaId, regionId);
    });

    $('#selDistr').change(function () {
        var dateFrom = $('#dateFrom').val();
        var dateTo = $('#dateTo').val();
        var nkaId = $('#selNka option:selected').data('value');
        var regionId = $('#selRegion option:selected').data('value');
        var distrId = $('#selDistr option:selected').data('value');
        clearAllFromDistr();
        loadMlkas(dateFrom, dateTo, nkaId, regionId, distrId);
    });

    $('#selMlka').change(function () {
        var dateFrom = $('#dateFrom').val();
        var dateTo = $('#dateTo').val();
        var nkaId = $('#selNka option:selected').data('value');
        var regionId = $('#selRegion option:selected').data('value');
        var distrId = $('#selDistr option:selected').data('value');
        var mlkaId = $('#selMlka option:selected').data('value');
        clearAllFromMlka();
        loadClients(dateFrom, dateTo, nkaId, regionId, distrId, mlkaId);
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
        window.location.href = 'mlka/getExcelReport?dateFrom=' + $('#dateFrom').val() +
                '&dateTo=' + $('#dateTo').val();
    });
});

function dateDiff(dateFrom, dateTo) {
    var date1 = new Date(dateFrom);
    var date2 = new Date(dateTo);
    var timeDiff = date2.getTime() - date1.getTime();
    var diffDays = Math.ceil(timeDiff / (1000 * 3600 * 24));
    return diffDays;
}

function dayOfWeek(sDate) {
    var date = new Date(sDate);
    var dayOfWeek = date.getDay();
    return dayOfWeek;
}

function clearAllFromDates() {
    $('#selNka option').remove();
    clearAllFromNka();
}

function clearAllFromNka() {
    $('#selRegion option').remove();
    clearNkaCriterias();
    clearAllFromRegion();
}

function clearAllFromRegion() {
    $('#selDistr option').remove();
    clearAllFromDistr();
}

function clearAllFromDistr() {
    $('#selMlka option').remove();
    clearAllFromMlka();
}

function clearAllFromMlka() {
    $('#addressTable tr').remove();
    $('#center_pane .photoBlock').remove();
    $('#clientId').text("ID: ");
    $('#clientType').text("Тип: ");
    clearCriteriasPane();
}

function clearNkaCriterias() {
    $('#mzCrit1Label').text('Критерий 1');
    $('#mzCrit2Label').text('Критерий 2');
    $('#mzCrit3Label').text('Критерий 3');

    $('#kCrit1Label').text('Критерий 1');
    $('#kCrit2Label').text('Критерий 2');
    $('#kCrit3Label').text('Критерий 3');

    $('#sCrit1Label').text('Критерий 1');
    $('#sCrit2Label').text('Критерий 2');
    $('#sCrit3Label').text('Критерий 3');
}

function clearCriteriasPane() {
    $('#mzPhoto').prop('checked', false);
    $('#mzCorrect').prop('checked', false);
    $('#mzCrit1').prop('checked', false);
    $('#mzCrit2').prop('checked', false);
    $('#mzCrit3').prop('checked', false);

    $('#kPhoto').prop('checked', false);
    $('#kCorrect').prop('checked', false);
    $('#kCrit1').prop('checked', false);
    $('#kCrit2').prop('checked', false);
    $('#kCrit3').prop('checked', false);

    $('#sPhoto').prop('checked', false);
    $('#sCorrect').prop('checked', false);
    $('#sCrit1').prop('checked', false);
    $('#sCrit2').prop('checked', false);
    $('#sCrit3').prop('checked', false);

    $('#oos').prop('checked', false);
    $('#comment').val('');
}

function loadNka(dateFrom, dateTo) {
    $('.datePicker').css('background-color', '');
    $('#loader').css('display', 'block');
    $.post('mlka/getNka',
        {
            dateFrom: dateFrom,
            dateTo: dateTo
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

function loadRegions(dateFrom, dateTo, nkaId) {
    $('.datePicker').css('background-color', '');
    $('#loader').css('display', 'block');
    console.log (dateFrom + " " + dateTo + " " + nkaId);
    $.post('mlka/getRegions',
        {
            dateFrom: dateFrom,
            dateTo: dateTo,
            nkaId: nkaId
        })
        .done (function (data) {
            checkForRedirect(data);
            $('#selRegion').append(data);
        })
        .fail (function() {
            showErrorPane();
        })
        .always (function (data) {
            $('#loader').css('display', 'none');
        });
}

function loadDistrs(dateFrom, dateTo, nkaId, regionId) {
    $('#loader').css('display', 'block');
    $.post('mlka/getDistrs',
        {
            dateFrom: dateFrom,
            dateTo: dateTo,
            nkaId: nkaId,
            regionId: regionId
        })
        .done (function (data) {
            checkForRedirect(data);
            $('#selDistr').append(data);
        })
        .fail( function() {
            showErrorPane()
        })
        .always (function (data) {
            $('#loader').css('display', 'none');
        });
}

function loadMlkas(dateFrom, dateTo, nkaId, regionId, distrId) {
    $('#loader').css('display', 'block');
    $.post('mlka/getMlkas',
        {
            dateFrom: dateFrom,
            dateTo: dateTo,
            nkaId: nkaId,
            regionId: regionId,
            distrId: distrId
        })
        .done(function (data) {
            checkForRedirect(data);
            $('#selMlka').append(data);
        })
        .fail(function() {
            showErrorPane()
        })
        .always(function (data) {
            $('#loader').css('display', 'none');
        })
}

function loadCriterias(nkaId) {
    if (nkaId === 1) {
        $('#mzCrit1Label').text('по центру');
        $('#mzCrit2Label').text('min 2 SKU');
        $('#mzCrit3Label').text('загруженность полки');

        $('#kCrit1Label').text('по центру');
        $('#kCrit2Label').text('min 2 SKU');
        $('#kCrit3Label').text('загруженность полки');

        $('#sCrit1Label').text('по центру');
        $('#sCrit2Label').text('min 2 SKU');
        $('#sCrit3Label').text('загруженность полки');
    } else if (nkaId === 2) {
        $('#mzCrit1Label').text('по центру');
        $('#mzCrit2Label').text('30% полки');
        $('#mzCrit3Label').text('верт. блок');

        $('#kCrit1Label').text('по центру');
        $('#kCrit2Label').text('30% полки');
        $('#kCrit3Label').text('верт. блок');

        $('#sCrit1Label').text('по центру');
        $('#sCrit2Label').text('30% полки');
        $('#sCrit3Label').text('верт. блок');
    }
}

function loadClients(dateFrom, dateTo, nkaId, regionId, distrId, mlkaId) {
    $('#loader').css('display', 'block');
    $.post('mlka/getClients',
        {
            dateFrom: dateFrom,
            dateTo: dateTo,
            nkaId: nkaId,
            regionId: regionId,
            distrId: distrId,
            mlkaId: mlkaId
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
    $.post('mlka/getPhotos',
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
    $.post('mlka/getSavedCriterias',
        {
            clientId: clientId,
            dateFrom: dateFrom,
            dateTo: dateTo
        })
        .done(function (data) {
            checkForRedirect(data);
            $('#mzPhoto').prop('checked', data.mzPhoto);
            $('#mzCorrect').prop('checked', data.mzCorr);
            $('#mzCrit1').prop('checked', data.mzCrit1);
            $('#mzCrit2').prop('checked', data.mzCrit2);
            $('#mzCrit3').prop('checked', data.mzCrit3);

            $('#kPhoto').prop('checked', data.kPhoto);
            $('#kCorrect').prop('checked', data.kCorr);
            $('#kCrit1').prop('checked', data.kCrit1);
            $('#kCrit2').prop('checked', data.kCrit2);
            $('#kCrit3').prop('checked', data.kCrit3);

            $('#sPhoto').prop('checked', data.sPhoto);
            $('#sCorrect').prop('checked', data.sCorr);
            $('#sCrit1').prop('checked', data.sCrit1);
            $('#sCrit2').prop('checked', data.sCrit2);
            $('#sCrit3').prop('checked', data.sCrit3);

            $('#oos').prop('checked', data.oos);
            $('#comment').val(data.comment);
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

        mzPhoto: $('#mzPhoto').is(':checked'),
        mzCorr: $('#mzCorrect').is(':checked'),
        mzCrit1: $('#mzCrit1').is(':checked'),
        mzCrit2: $('#mzCrit2').is(':checked'),
        mzCrit3: $('#mzCrit3').is(':checked'),

        kPhoto: $('#kPhoto').is(':checked'),
        kCorr: $('#kCorrect').is(':checked'),
        kCrit1: $('#kCrit1').is(':checked'),
        kCrit2: $('#kCrit2').is(':checked'),
        kCrit3: $('#kCrit3').is(':checked'),

        sPhoto: $('#sPhoto').is(':checked'),
        sCorr: $('#sCorrect').is(':checked'),
        sCrit1: $('#sCrit1').is(':checked'),
        sCrit2: $('#sCrit2').is(':checked'),
        sCrit3: $('#sCrit3').is(':checked'),

        oos: $('#oos').is(':checked'),
        comment: $('#comment').val()
    }

    $.post('mlka/saveCriterias',
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