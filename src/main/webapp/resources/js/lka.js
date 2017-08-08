$(function () {
    $('#dateFrom').change(function () {
        var dateFrom = this.value;
        $('#dateTo').val(dateFrom);
        var dateDiff = 0;
        clearAllFromDates();
        loadRegions(dateFrom, dateFrom);
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
            loadRegions(dateFrom, dateTo);
        } else {
            $('.datePicker').css('background-color', 'red');
        }
        if (dateDiffer === 0) {
            $('#saveButton').removeClass('hidden');
        } else {
            $('#saveButton').addClass('hidden');
        }
    });

    $('#selRegion').change(function () {
        var dateFrom = $('#dateFrom').val();
        var dateTo = $('#dateTo').val();
        var regionId = $('#selRegion option:selected').data('value');
        clearAllFromRegion();
        loadDistrs(dateFrom, dateTo, regionId);
    });

    $('#selDistr').change(function () {
        var dateFrom = $('#dateFrom').val();
        var dateTo = $('#dateTo').val();
        var regionId = $('#selRegion option:selected').data('value');
        var distrId = $('#selDistr option:selected').data('value');
        clearAllFromDistr();
        loadLkas(dateFrom, dateTo, regionId, distrId);
    });

    $('#selLka').change(function () {
        var dateFrom = $('#dateFrom').val();
        var dateTo = $('#dateTo').val();
        var regionId = $('#selRegion option:selected').data('value');
        var distrId = $('#selDistr option:selected').data('value');
        var lkaId = $('#selLka option:selected').data('value');
        clearAllFromLka();
        loadCriterias(lkaId);
        loadClients(dateFrom, dateTo, regionId, distrId, lkaId);
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
        window.location.href = 'lka/getExcelReport?dateFrom=' + $('#dateFrom').val() +
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
    $('#selRegion option').remove();
    clearAllFromRegion();
}

function clearAllFromRegion() {
    $('#selDistr option').remove();
    clearAllFromDistr();
}

function clearAllFromDistr() {
    $('#selLka option').remove();
    clearAllFromLka();
}

function clearAllFromLka() {
    $('#addressTable tr').remove();
    $('#center_pane .photoBlock').remove();
    $('#clientId').text("ID: ");
    $('#clientType').text("Тип: ");
    clearCriteriasPane();

    $('#mzInfoBlock').removeClass('hidden');
    $('#kInfoBlock').removeClass('hidden');
    $('#sInfoBlock').removeClass('hidden');
    $('#mInfoBlock').removeClass('hidden');

    $('#mzCrit1Label').text('Критерий 1');
    $('#mzCrit2Label').text('Критерий 2');

    $('#kCrit1Label').text('Критерий 1');
    $('#kCrit2Label').text('Критерий 2');

    $('#sCrit1Label').text('Критерий 1');
    $('#sCrit2Label').text('Критерий 2');

    $('#mCrit1Label').text('Критерий 1');
    $('#mCrit2Label').text('Критерий 2');
}

function clearCriteriasPane() {
    $('#mzPhoto').prop('checked', false);
    $('#mzCorrect').prop('checked', false);
    $('#mzSP').prop('checked', false);
    $('#mzCrit1').prop('checked', false);
    $('#mzCrit2').prop('checked', false);

    $('#kPhoto').prop('checked', false);
    $('#kCorrect').prop('checked', false);
    $('#kCrit1').prop('checked', false);
    $('#kCrit2').prop('checked', false);

    $('#sPhoto').prop('checked', false);
    $('#sCorrect').prop('checked', false);
    $('#sCrit1').prop('checked', false);
    $('#sCrit2').prop('checked', false);

    $('#mPhoto').prop('checked', false);
    $('#mCorrect').prop('checked', false);
    $('#mCrit1').prop('checked', false);
    $('#mCrit2').prop('checked', false);

    $('#oos').prop('checked', false);
    $('#comment').val('');
}

function loadRegions(dateFrom, dateTo) {
    $('.datePicker').css('background-color', '');
    $('#loader').css('display', 'block');
    $.post('lka/getRegions',
        {
            dateFrom: dateFrom,
            dateTo: dateTo
        })
        .done (function (data) {
            checkForRedirect(data);
            console.log(data);
            clearAllFromDates();
            $('#selRegion').append(data);
        })
        .fail (function() {
            showErrorPane();
        })
        .always (function (data) {
            $('#loader').css('display', 'none');
        });
}

function loadDistrs(dateFrom, dateTo, regionId) {
    $('#loader').css('display', 'block');
    $.post('lka/getDistrs',
        {
            dateFrom: dateFrom,
            dateTo: dateTo,
            regionId: regionId
        })
        .done (function (data) {
            checkForRedirect(data);
            console.log(data);
            $('#selDistr').append(data);
        })
        .fail( function() {
            showErrorPane()
        })
        .always (function (data) {
            $('#loader').css('display', 'none');
        });
}

function loadLkas(dateFrom, dateTo, regionId, distrId) {
    $('#loader').css('display', 'block');
    $.post('lka/getLkas',
        {
            dateFrom: dateFrom,
            dateTo: dateTo,
            regionId: regionId,
            distrId: distrId
        })
        .done(function (data) {
            checkForRedirect(data);
            console.log(data);
            $('#selLka').append(data);
        })
        .fail(function() {
            showErrorPane()
        })
        .always(function (data) {
            $('#loader').css('display', 'none');
        })
}

function loadCriterias(lkaId) {
    $.post('lka/getCriterias',
        {
            lkaId: lkaId
        })
        .done(function (data) {
            checkForRedirect(data);
            console.log(data);
            if (data.hasMz) {
                $('#mzInfoBlock').removeClass('hidden');
                $('#mzCrit1Label').text(data.crit1Mz);
                $('#mzCrit2Label').text(data.crit2);
            } else {
                $('#mzInfoBlock').addClass('hidden');
            }
            if (data.hasK) {
                $('#kInfoBlock').removeClass('hidden');
                $('#kCrit1Label').text(data.crit1K);
                $('#kCrit2Label').text(data.crit2);
            } else {
                $('#kInfoBlock').addClass('hidden');
            }
            if (data.hasS) {
                $('#sInfoBlock').removeClass('hidden');
                $('#sCrit1Label').text(data.crit1S);
                $('#sCrit2Label').text(data.crit2);
            } else {
                $('#sInfoBlock').addClass('hidden');
            }
            if (data.hasM) {
                $('#mInfoBlock').removeClass('hidden');
                $('#mCrit1Label').text(data.crit1M);
                $('#mCrit2Label').text(data.crit2);
            } else {
                $('#mInfoBlock').addClass('hidden');
            }
        })
}

function loadClients(dateFrom, dateTo, regionId, distrId, lkaId) {
    $('#loader').css('display', 'block');
    $.post('lka/getClients',
        {
            dateFrom: dateFrom,
            dateTo: dateTo,
            regionId: regionId,
            distrId: distrId,
            lkaId: lkaId
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
    $.post('lka/getPhotos',
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
    $.post('lka/getSavedCriterias',
        {
            clientId: clientId,
            dateFrom: dateFrom,
            dateTo: dateTo
        })
        .done(function (data) {
            checkForRedirect(data);
            console.log(data);
            $('#mzPhoto').prop('checked', data.hasPhotoMz);
            $('#mzCorrect').prop('checked', data.correctMz);
            $('#mzSP').prop('checked', data.hasAddProdMz);
            $('#mzCrit1').prop('checked', data.crit1Mz);
            $('#mzCrit2').prop('checked', data.crit2Mz);

            $('#kPhoto').prop('checked', data.hasPhotoK);
            $('#kCorrect').prop('checked', data.correctK);
            $('#kCrit1').prop('checked', data.crit1K);
            $('#kCrit2').prop('checked', data.crit2K);

            $('#sPhoto').prop('checked', data.hasPhotoS);
            $('#sCorrect').prop('checked', data.correctS);
            $('#sCrit1').prop('checked', data.crit1S);
            $('#sCrit2').prop('checked', data.crit2S);

            $('#mPhoto').prop('checked', data.hasPhotoM);
            $('#mCorrect').prop('checked', data.correctM);
            $('#mCrit1').prop('checked', data.crit1M);
            $('#mCrit2').prop('checked', data.crit2M);

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

    var clientCriterias = {
        clientId: clientId,

        hasMz: !($('#mzInfoBlock').hasClass('hidden')),
        hasPhotoMz: $('#mzPhoto').is(':checked'),
        correctMz: $('#mzCorrect').is(':checked'),
        hasAddProdMz: $('#mzSP').is(':checked'),
        crit1Mz: $('#mzCrit1').is(':checked'),
        crit2Mz: $('#mzCrit2').is(':checked'),

        hasK: !($('#kInfoBlock').hasClass('hidden')),
        hasPhotoK: $('#kPhoto').is(':checked'),
        correctK: $('#kCorrect').is(':checked'),
        crit1K: $('#kCrit1').is(':checked'),
        crit2K: $('#kCrit2').is(':checked'),

        hasS: !($('#sInfoBlock').hasClass('hidden')),
        hasPhotoS: $('#sPhoto').is(':checked'),
        correctS: $('#sCorrect').is(':checked'),
        crit1S: $('#sCrit1').is(':checked'),
        crit2S: $('#sCrit2').is(':checked'),

        hasM: !($('#mInfoBlock').hasClass('hidden')),
        hasPhotoM: $('#mPhoto').is(':checked'),
        correctM: $('#mCorrect').is(':checked'),
        crit1M: $('#mCrit1').is(':checked'),
        crit2M: $('#mCrit2').is(':checked'),

        oos: $('#oos').is(':checked'),
        comment: $('#comment').val()
    };

    $.post('lka/saveCriterias',
        {
            clientCrit: JSON.stringify(clientCriterias),
            dateFrom: $('#dateFrom').val(),
            dateTo: $('#dateTo').val()
        })
        .done(function (data) {
            checkForRedirect(data);
            console.log(data);
            console.log(data);
            showSavedSuccessfullyPane();
        })
        .fail(function (data) {
            showErrorPane();
        })
        .always(function (data) {
            $('#loader').css('display', 'none');
        })
}