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
        var distrId = $('#selDistr option:selected').data('value');
        clearAllFromDistr();
        loadChannels(dateFrom, dateTo, distrId);
    });

    $('#selChannel').change(function() {
        var dateFrom = $('#dateFrom').val();
        var dateTo = $('#dateTo').val();
        var regionId = $('#selRegion option:selected').data('value');
        var distrId = $('#selDistr option:selected').data('value');
        var channelId = $('#selChannel option:selected').data('value');
        clearAllFromChannel();
        if (channelId === 1) {
            $('#selLka').parent().removeClass('hidden');
            loadLkas(dateFrom, dateTo, regionId, distrId);
        } else {
            $('#selLka').parent().addClass('hidden');
            loadClients(dateFrom, dateTo, regionId, distrId, channelId, 0);
        }
    });

    $('#selLka').change(function () {
        var dateFrom = $('#dateFrom').val();
        var dateTo = $('#dateTo').val();
        var regionId = $('#selRegion option:selected').data('value');
        var distrId = $('#selDistr option:selected').data('value');
        var lkaId = $('#selLka option:selected').data('value');
        clearAllFromLka();
        loadClients(dateFrom, dateTo, regionId, distrId, 1, lkaId);
    });

    $('#addressTable').on('click', '.addr', function() {
        $('tr.addr').removeClass('addressSelected');
        $('#center_pane .photoBlock').remove();

        $('#dmpCountSelector').val('1');
        clearAllCriteriaPanes();
        resizeTabs(1);

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

    $('#dmpCountSelector').change(function () {
        var tabsCount = $('#dmpCountSelector option:selected').data('value');
        resizeTabs(tabsCount);
    });

    $('.allOk').on('click', function (data) {
        var tab = $(this).parent().parent();
        checkAllOnTab(tab);
    });

    $('#saveButton').on('click', function () {
        var addrRow = $('#addressTable tr.addressSelected');
        var clientId = addrRow.children().eq(2).text();
        saveCriteriasByClient(clientId);
        addrRow.addClass('addressChecked');
    });

    $('#clearButton').on('click', function () {
        clearAllCriteriaPanes();

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
    $('#selChannel option').remove();
    clearAllFromChannel();
}

function clearAllFromChannel() {
    $('#selLka option').remove();
    clearAllFromLka();
}

function clearAllFromLka() {
    $('#addressTable tr').remove();
    $('#center_pane .photoBlock').remove();
    $('#clientId').text("ID: ");
    $('#clientType').text("Тип: ");
    var dmpPanes = $('.dmp');
    for (var i = 0; i < dmpPanes.length; i++) {
        clearCriteriasPane(dmpPanes.eq(i));
    }
}

function clearAllCriteriaPanes() {
    var dmpPanes = $('.dmp');
    for (var i = 0; i < dmpPanes.length; i++) {
        clearCriteriasPane(dmpPanes.eq(i));
    }
}

function clearCriteriasPane(criteriasPane) {
    criteriasPane.find('.isPhotoCorr').prop('checked', false);
    criteriasPane.find('.keyWord').prop('checked', false);

    criteriasPane.find('.mz').prop('checked', false);
    criteriasPane.find('.k').prop('checked', false);
    criteriasPane.find('.addProd').prop('checked', false);
    criteriasPane.find('.s').prop('checked', false);
    criteriasPane.find('.m').prop('checked', false);

    criteriasPane.find('.minSize').prop('checked', false);
    criteriasPane.find('.tmaProd').prop('checked', false);
    criteriasPane.find('.price').prop('checked', false);
    criteriasPane.find('.fill80').prop('checked', false);
    criteriasPane.find('.place').prop('checked', false);

    criteriasPane.find('.commentArea').val('');
}

function loadRegions(dateFrom, dateTo) {
    $('.datePicker').css('background-color', '');
    $('#loader').css('display', 'block');
    $.post('lkaDmp/getRegions',
        {
            dateFrom: dateFrom,
            dateTo: dateTo
        })
        .done (function (data) {
            checkForRedirect(data);
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
    $.post('lkaDmp/getDistrs',
        {
            dateFrom: dateFrom,
            dateTo: dateTo,
            regionId: regionId
        })
        .done (function (data) {
            checkForRedirect(data);
            $('#selDistr').append(data);
        })
        .fail( function() {
            showErrorPane();
        })
        .always (function (data) {
            $('#loader').css('display', 'none');
        });
}

function loadChannels(dateFrom, dateTo, distrId) {
    $('#loader').css('display', 'block');
    $.post('lkaDmp/getChannels',
        {
            dateFrom: dateFrom,
            dateTo: dateTo,
            distrId: distrId
        })
        .done(function (data) {
            checkForRedirect(data);
            $('#selChannel').append(data);
        })
        .fail(function() {
            showErrorPane();
        })
        .always(function (data) {
            $('#loader').css('display', 'none');
        });
}

function loadLkas(dateFrom, dateTo, regionId, distrId) {
    $('#loader').css('display', 'block');
    $.post('lkaDmp/getLkas',
        {
            dateFrom: dateFrom,
            dateTo: dateTo,
            regionId: regionId,
            distrId: distrId
        })
        .done(function (data) {
            checkForRedirect(data);
            $('#selLka').append(data);
        })
        .fail(function() {
            showErrorPane()
        })
        .always(function (data) {
            $('#loader').css('display', 'none');
        })
}

function loadClients(dateFrom, dateTo, regionId, distrId, channelId, lkaId) {
    $('#loader').css('display', 'block');
    $.post('lkaDmp/getClients',
        {
            dateFrom: dateFrom,
            dateTo: dateTo,
            regionId: regionId,
            distrId: distrId,
            channelId: channelId,
            lkaId: lkaId
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
    $.post('lkaDmp/getPhotos',
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
    $.post('lkaDmp/getSavedCriterias',
        {
            clientId: clientId,
            dateFrom: dateFrom,
            dateTo: dateTo
        })
        .done(function (data) {
            checkForRedirect(data);
            $('#mzPhoto').prop('checked', data.hasPhotoMz);
            $('#mzCorrect').prop('checked', data.isCorrectMz);
            $('#mzSP').prop('checked', data.hasAddProdMz);
            $('#mzCrit1').prop('checked', data.crit1Mz);
            $('#mzCrit2').prop('checked', data.crit2Mz);

            $('#kPhoto').prop('checked', data.hasPhotoK);
            $('#kCorrect').prop('checked', data.isCorrectK);
            $('#kCrit1').prop('checked', data.crit1K);
            $('#kCrit2').prop('checked', data.crit2K);

            $('#sPhoto').prop('checked', data.hasPhotoS);
            $('#sCorrect').prop('checked', data.isCorrectS);
            $('#sCrit1').prop('checked', data.crit1S);
            $('#sCrit2').prop('checked', data.crit2S);

            $('#mPhoto').prop('checked', data.hasPhotoM);
            $('#mCorrect').prop('checked', data.isCorrectM);
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

    var dmpCount = $('#dmpCountSelector').find(':selected').data('value');

    var tabContents = $('.dmp');
    var array = [];
    for (var i = 0; i < dmpCount; i++) {
        var dmp = {
            clientId: clientId,
            dmpNum: i,
            dmpCount: dmpCount,

            isPhotoCorr: tabContents.eq(i).find('.isPhotoCorr').is(':checked'),
            keyword: tabContents.eq(i).find('.keyword').is(':checked'),

            mz: tabContents.eq(i).find('.mz').is(':checked'),
            k: tabContents.eq(i).find('.k').is(':checked'),
            addProd: tabContents.eq(i).find('.addProd').is(':checked'),
            s: tabContents.eq(i).find('.s').is(':checked'),
            m: tabContents.eq(i).find('.m').is(':checked'),

            minSize: tabContents.eq(i).find('.minSize').is(':checked'),
            tmaProd: tabContents.eq(i).find('.tmaProd').is(':checked'),
            price: tabContents.eq(i).find('.price').is(':checked'),
            fill80: tabContents.eq(i).find('.fill80').is(':checked'),
            place: tabContents.eq(i).find('.place').is(':checked'),

            comment: tabContents.eq(i).find('.commentArea').val()
        };
        array.push(dmp);
    }

    $.post('lkaDmp/saveCriterias',
        {
            dmpArray: JSON.stringify(array),
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
};

function resizeTabs(tabsCount) {
    var tablinks = $('.tablinks');
    for (var i = 0; i < tablinks.length; i++) {
        tablinks.eq(i).removeClass('hidden');
        if (i >= tabsCount) {
            tablinks.eq(i).addClass('hidden');
        }
        tablinks.eq(i).removeClass('active');
        if (i == 0) {
            tablinks.eq(i).addClass('active');
        }
    }

    var tabContents = $('.dmp');
    for (var i = 0; i < tabContents.length; i++) {
        if (i == 0) {
            tabContents.eq(i).removeClass('hidden');
        } else {
            tabContents.eq(i).addClass('hidden');
        }

        if (i >= tabsCount) {
            clearCriteriasPane(tabContents.eq(i));
        }
    }
};

function openCity(evt, dmpNum) {
    var i, tabcontent, tablinks;

    tabcontent = $(".dmp");
    for (i = 0; i < tabcontent.length; i++) {
        tabcontent.eq(i).addClass("hidden");
    }

    tablinks = $(".tablinks");
    for (i = 0; i < tablinks.length; i++) {
        tablinks.eq(i).removeClass('active');
    }

    $('#' + dmpNum).removeClass('hidden');
    evt.currentTarget.className += " active";
}

function checkAllOnTab(tab) {
    tab.find('.isPhotoCorr').prop('checked', true);
    tab.find('.keyWord').prop('checked', true);

    tab.find('.minSize').prop('checked', true);
    tab.find('.tmaProd').prop('checked', true);
    tab.find('.price').prop('checked', true);
    tab.find('.fill80').prop('checked', true);
    tab.find('.place').prop('checked', true);
}