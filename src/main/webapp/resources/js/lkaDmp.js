$(function () {
    $('#dateFrom').change(function () {
        var dateFrom = this.value;
        var dateTo = new Date(dateFrom);
        dateTo.setDate(dateTo.getDate() + 6);
        var dateToS = dateTo.getFullYear() + '-'
            + ('0' + (dateTo.getMonth() + 1)).slice(-2) + '-'
            + ('0' + dateTo.getDate()).slice(-2);
        $('#dateTo').val(dateToS);
        checkDatesAndLoadRegions(dateFrom, dateToS);
    });

    $('#dateTo').change(function () {
        var dateFrom = $('#dateFrom').val();
        var dateTo = this.value;
        checkDatesAndLoadRegions(dateFrom, dateTo);
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
        if ($(this).children().eq(4).text() > 0) {
            loadSavedCriterias(client_id, dateFrom, dateTo);
        }
    });

    $('#content_pane').on('dblclick', '.photoBlock img', function (data) {
        var num = $(this).data('num');
        var url = $(this).attr('src');
        var date = $(this).parent().find('.photoDate').text();
        var dateAdd = $(this).parent().find('.addDate').text();
        var comment = $(this).parent().find('textarea').val();
        if ($(this).parent().hasClass('photoChecked')) {
            $('#fullPhotoCont').addClass('photoChecked');
        } else {
            $('#fullPhotoCont').removeClass('photoChecked');
        }
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
        addrRow.children().eq(4).text(1);
    });

    $('#clearButton').on('click', function () {
        clearAllCriteriaPanes();
    });

    $('#to_xlsx').on('click', function (e) {
        e.preventDefault();
        window.location.href = 'lkaDmp/getExcelReport?dateFrom=' + $('#dateFrom').val() +
                '&dateTo=' + $('#dateTo').val();
    });
});

function checkDatesAndLoadRegions(dateFrom, dateTo) {
    var dateFromD = new Date(dateFrom);
    var dateToD = new Date(dateTo);
    var timeDiff = dateToD.getTime() - dateFromD.getTime();
    var diffDays = Math.ceil(timeDiff / (1000 * 3600 * 24));
    var dayFrom = dateFromD.getDay();
    clearAllFromDates();
    if (diffDays === 6 && dayFrom === 1) {
        $('.datePicker').css('background-color', '');
        loadRegions(dateFrom, dateTo);
        $('#saveButton').removeClass('hidden');
        $('#to_xlsx').removeClass('hidden');
    } else {
        $('.datePicker').css('background-color', 'red');
        $('#saveButton').addClass('hidden');
        $('#to_xlsx').addClass('hidden');
    }
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
    resizeTabs(1);
}

function clearAllCriteriaPanes() {
    var dmpPanes = $('.dmp');
    for (var i = 0; i < dmpPanes.length; i++) {
        clearCriteriasPane(dmpPanes.eq(i));
    }
}

function clearCriteriasPane(criteriasPane) {
    criteriasPane.find('.photoCorr').prop('checked', false);
    criteriasPane.find('.keyWord').prop('checked', false);

    criteriasPane.find('.mz').prop('checked', false);
    criteriasPane.find('.mzRicco').prop('checked', false);
    criteriasPane.find('.mzRSpec').prop('checked', false);
    criteriasPane.find('.mzMilad').prop('checked', false);
    criteriasPane.find('.mzMSpec').prop('checked', false);
    criteriasPane.find('.k').prop('checked', false);
    criteriasPane.find('.s').prop('checked', false);
    criteriasPane.find('.sSpec').prop('checked', false);
    criteriasPane.find('.m').prop('checked', false);
    criteriasPane.find('.mSpec').prop('checked', false);

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

            var dmpCount = data.length;
            resizeTabs(dmpCount);

            for (var i = 0; i < dmpCount; i++) {
                setDmpCriterias(i, data[i]);
            }
        })
        .fail(function () {
            showErrorPane();
        })
}

function setDmpCriterias(dmpNum, dmpCriterias) {
    var dmp = $('.dmp').eq(dmpNum);

    dmp.find('.photoCorr').prop('checked', dmpCriterias.photoCorr);
    dmp.find('.keyword').prop('checked', dmpCriterias.keyword);

    dmp.find('.mz').prop('checked', dmpCriterias.mz);
    dmp.find('.mzRicco').prop('checked', dmpCriterias.mzRicco);
    dmp.find('.mzRSpec').prop('checked', dmpCriterias.mzRSpec);
    dmp.find('.mzMilad').prop('checked', dmpCriterias.mzMilad);
    dmp.find('.mzMSpec').prop('checked', dmpCriterias.mzMSpec);
    dmp.find('.k').prop('checked', dmpCriterias.k);
    dmp.find('.s').prop('checked', dmpCriterias.s);
    dmp.find('.sSpec').prop('checked', dmpCriterias.sSpec);
    dmp.find('.m').prop('checked', dmpCriterias.m);
    dmp.find('.mSpec').prop('checked', dmpCriterias.mSpec);

    dmp.find('.minSize').prop('checked', dmpCriterias.minSize);
    dmp.find('.tmaProd').prop('checked', dmpCriterias.tmaProd);
    dmp.find('.price').prop('checked', dmpCriterias.price);
    dmp.find('.fill80').prop('checked', dmpCriterias.fill80);
    dmp.find('.place').prop('checked', dmpCriterias.place);

    dmp.find('.commentArea').val(dmpCriterias.comment);
}

function fillDatasOnPhotoShow(newPhotoBlockNum) {
    var newUrl = $('#content_pane img[data-num=' + newPhotoBlockNum + ']').attr('src');
    var newPhotoBlock = $('#content_pane img[data-num=' + newPhotoBlockNum + ']').parent();
    var date = newPhotoBlock.find('.photoDate').text();
    var dateAdd = newPhotoBlock.find('.addDate').text();
    var comment = newPhotoBlock.find('textarea').val();
    if ($(newPhotoBlock).hasClass('photoChecked')) {
        $('#fullPhotoCont').addClass('photoChecked');
    } else {
        $('#fullPhotoCont').removeClass('photoChecked');
    }
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

    var photos = $('.photoBlock');
    var photoUrls = [];

    for (var i = 0; i < photos.length; i++) {
        photoUrls.push(photos.eq(i).find('img').attr('src'));
    }

    for (var i = 0; i < dmpCount; i++) {
        var dmp = {
            clientId: clientId,
            dmpNum: i,
            dmpCount: dmpCount,

            photoCorr: tabContents.eq(i).find('.photoCorr').is(':checked'),
            keyword: tabContents.eq(i).find('.keyword').is(':checked'),

            mz: tabContents.eq(i).find('.mz').is(':checked'),
            mzRicco: tabContents.eq(i).find('.mzRicco').is(':checked'),
            mzRSpec: tabContents.eq(i).find('.mzRSpec').is(':checked'),
            mzMilad: tabContents.eq(i).find('.mzMilad').is(':checked'),
            mzMSpec: tabContents.eq(i).find('.mzMSpec').is(':checked'),
            k: tabContents.eq(i).find('.k').is(':checked'),
            s: tabContents.eq(i).find('.s').is(':checked'),
            sSpec: tabContents.eq(i).find('.sSpec').is(':checked'),
            m: tabContents.eq(i).find('.m').is(':checked'),
            mSpec: tabContents.eq(i).find('.mSpec').is(':checked'),

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
            photoUrls: JSON.stringify(photoUrls),
            dateFrom: $('#dateFrom').val(),
            dateTo: $('#dateTo').val()
        })
        .done(function (data) {
            checkForRedirect(data);
            showSavedSuccessfullyPane();
            addCheckedClassToPhotos();
        })
        .fail(function (data) {
            showErrorPane();
        })
        .always(function (data) {
            $('#loader').css('display', 'none');
        })
};

function addCheckedClassToPhotos() {
    var photos = $('.photoBlock');
    for (var i = 0; i < photos.length; i++) {
        photos.eq(i).addClass('photoChecked');
    }
}

function resizeTabs(tabsCount) {
    $('#dmpCountSelector').val(tabsCount);
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

function openDmp(evt, dmpNum) {
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
    tab.find('.photoCorr').prop('checked', true);
    tab.find('.keyWord').prop('checked', true);

    tab.find('.minSize').prop('checked', true);
    tab.find('.tmaProd').prop('checked', true);
    tab.find('.price').prop('checked', true);
    tab.find('.fill80').prop('checked', true);
    tab.find('.place').prop('checked', true);
}