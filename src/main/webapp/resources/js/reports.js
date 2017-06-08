$(function() {
    $('.group_title').on('click', function (data) {
        $(this).parent().children('.report_link').toggle(500);
    });
});
