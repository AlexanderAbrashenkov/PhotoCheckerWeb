$(function () {
    $('#dateFrom').change(function () {
        var dateFrom = this.value;
        var dateTo = $('#dateTo').val();
        clearAllFromDates();
        if (dateTo >= dateFrom) {
            $('.datePicker').css('background-color', '');
            loadNstObl(dateFrom, dateTo);
        } else {
            $('.datePicker').css('background-color', 'red');
        }
    });

    $('#dateTo').change(function () {
        var dateFrom = $('#dateFrom').val();
        var dateTo = this.value;
        clearAllFromDates();
        if (dateTo >= dateFrom) {
            $('.datePicker').css('background-color', '');
            loadNstObl(dateFrom, dateTo);
        } else {
            $('.datePicker').css('background-color', 'red');
        }
    });

    $('#selNstObl').change(function () {
        var dateFrom = $('#dateFrom').val();
        var dateTo = $('#dateTo').val();
        var nstOblId = $('#selNstObl option:selected').data('value');
        clearAllFromNstObl();
        loadClients(dateFrom, dateTo, nstOblId);
    });

    $('#addressTable').on('click', '.addr', function() {
        $('tr.addr').removeClass('addressSelected');
        $('#center_pane .photoBlock').remove();
        clearCriteriasPane();
        var client_id = $(this).children().eq(1).text();
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
        date = date.replace("Дата: ", "");
        var count = $('#center_pane .photoBlock:last-child img').data('num');
        $('#showPhoto').css('display', 'block');
        $('#fullPhotoCont img').attr('src', url);
        $('#fullPhotoCont img').attr('data-zoom-image', url);
        $('#fullPhotoNum').text(num + " /");
        $('#fullPhotoCount').text(count);
        $('#fullPhotoDate').text(date);
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

    $('.commVars').on('change', function () {
        var textSel = $(this).find('option:selected').text();
        var existText = $(this).parent().find('.commInput').val();
        if (existText.length > 0) {
            textSel = existText + "; " + textSel;
        }
        $(this).parent().find('.commInput').val(textSel);
        $(this).val('');
    })

    $('#saveButton').on('click', function () {
        var addrRow = $('#addressTable tr.addressSelected');
        var clientId = addrRow.children().eq(1).text();
        saveCriteriasByClient(clientId);
        addrRow.addClass('addressChecked');
    });

    $('#clearButton').on('click', function () {
        clearCriteriasPane();
    });

    $('#to_xlsx').on('click', function (e) {
        e.preventDefault();
        window.location.href = 'nst/getExcelReport?dateFrom=' + $('#dateFrom').val() +
                '&dateTo=' + $('#dateTo').val();
    });
});

function clearAllFromDates() {
    $('#selNstObl option').remove();
    clearAllFromNstObl();
}

function clearAllFromNstObl() {
    $('#addressTable tr').remove();
    $('#center_pane .photoBlock').remove();
    clearCriteriasPane();
}

function clearCriteriasPane() {
    $('#selVisitCount').val(1);

    $('#mzMatrix').prop('checked', true);
    $('#mzPhoto').prop('checked', false);
    $('#mzBorders').prop('checked', false);
    $('#mzVert').prop('checked', false);
    $('#mz30').prop('checked', false);
    $('#mzCenter').prop('checked', false);
    $('#mzCommInput').val('');

    $('#ksMatrix').prop('checked', true);
    $('#ksPhoto').prop('checked', false);
    $('#ksBorders').prop('checked', false);
    $('#ksVert').prop('checked', false);
    $('#ks30').prop('checked', false);
    $('#ksCenter').prop('checked', false);
    $('#ksCommInput').val('');

    $('#mMatrix').prop('checked', true);
    $('#mPhoto').prop('checked', false);
    $('#mBorders').prop('checked', false);
    $('#mVert').prop('checked', false);
    $('#mCenter').prop('checked', false);
    $('#mCommInput').val('');
}

function loadNstObl(dateFrom, dateTo) {
    $('#loader').css('display', 'block');
    $.post('nst/getNstObl',
        {
            dateFrom: dateFrom,
            dateTo: dateTo
        })
        .done (function (data) {
            checkForRedirect(data);
            clearAllFromDates();
            $('#selNstObl').append(data);
        })
        .fail (function() {
            showErrorPane();
        })
        .always (function (data) {
            $('#loader').css('display', 'none');
        });
}

function loadClients(dateFrom, dateTo, nstOblId) {
    $('#loader').css('display', 'block');
    $.post('nst/getClients',
        {
            dateFrom: dateFrom,
            dateTo: dateTo,
            nstOblId: nstOblId
        })
        .done(function (data) {
            checkForRedirect(data);
            console.log(data);
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
    $.post('nst/getPhotos',
        {
            dateFrom: dateFrom,
            dateTo: dateTo,
            clientId: clientId
        })
        .done(function (data) {
            checkForRedirect(data);
            console.log(data);
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
    $.post('nst/getSavedCriterias',
        {
            clientId: clientId,
            dateFrom: dateFrom,
            dateTo: dateTo
        })
        .done(function (data) {
            checkForRedirect(data);
            console.log(data);
            $('#selVisitCount').val(data.visitCount);

            $('#mzMatrix').prop('checked', data.mzMatrix);
            $('#mzPhoto').prop('checked', data.mzPhoto);
            $('#mzBorders').prop('checked', data.mzBorders);
            $('#mzVert').prop('checked', data.mzVert);
            $('#mz30').prop('checked', data.mz30);
            $('#mzCenter').prop('checked', data.mzCenter);
            $('#mzCommInput').val(data.mzComment);

            $('#ksMatrix').prop('checked', data.ksMatrix);
            $('#ksPhoto').prop('checked', data.ksPhoto);
            $('#ksBorders').prop('checked', data.ksBorders);
            $('#ksVert').prop('checked', data.ksVert);
            $('#ks30').prop('checked', data.ks30);
            $('#ksCenter').prop('checked', data.ksCenter);
            $('#ksCommInput').val(data.ksComment);

            $('#mMatrix').prop('checked', data.mMatrix);
            $('#mPhoto').prop('checked', data.mPhoto);
            $('#mBorders').prop('checked', data.mBorders);
            $('#mVert').prop('checked', data.mVert);
            $('#mCenter').prop('checked', data.mCenter);
            $('#mCommInput').val(data.mComment);
        })
        .fail(function () {
            showErrorPane();
        })
}

function fillDatasOnPhotoShow(newPhotoBlockNum) {
    var newUrl = $('#content_pane img[data-num=' + newPhotoBlockNum + ']').attr('src');
    var newPhotoBlock = $('#content_pane img[data-num=' + newPhotoBlockNum + ']').parent();
    var date = newPhotoBlock.find('.photoDate').text();
    date = date.replace("Дата: ", "");
    $('#fullPhotoDate').text(date);
    $('#fullPhotoCont img').attr('src', newUrl);
    $('#fullPhotoNum').text(newPhotoBlockNum + " /");
    $('#fullPhotoCont img').css('max-width', '100%');
    $('#fullPhotoCont img').css('max-height', '100%');
    $('#fullPhotoCont img').removeClass('rotateLeft');
    $('#fullPhotoCont img').removeClass('rotateRight');
}

function saveCriteriasByClient(clientId) {
    $('#loader').css('display', 'block');

    var clientCriterias = {
        clientId: clientId,
        visitCount: $('#selVisitCount option:selected').data('value'),

        mzMatrix: $('#mzMatrix').is(':checked'),
        mzPhoto: $('#mzPhoto').is(':checked'),
        mzBorders: $('#mzBorders').is(':checked'),
        mzVert: $('#mzVert').is(':checked'),
        mz30: $('#mz30').is(':checked'),
        mzCenter: $('#mzCenter').is(':checked'),
        mzComment: $('#mzCommInput').val(),

        ksMatrix: $('#ksMatrix').is(':checked'),
        ksPhoto: $('#ksPhoto').is(':checked'),
        ksBorders: $('#ksBorders').is(':checked'),
        ksVert: $('#ksVert').is(':checked'),
        ks30: $('#ks30').is(':checked'),
        ksCenter: $('#ksCenter').is(':checked'),
        ksComment: $('#ksCommInput').val(),

        mMatrix: $('#mMatrix').is(':checked'),
        mPhoto: $('#mPhoto').is(':checked'),
        mBorders: $('#mBorders').is(':checked'),
        mVert: $('#mVert').is(':checked'),
        mCenter: $('#mCenter').is(':checked'),
        mComment: $('#mCommInput').val()
    };

    $.post('nst/saveCriterias',
        {
            crit: JSON.stringify(clientCriterias),
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