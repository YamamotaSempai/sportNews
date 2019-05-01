$(function () {
    $('#card__button-btn').click(function () {
        deletePost(this);
    });
});

function deletePost(caller) {
    var POST_ID = caller.getAttribute('id_post');

    $.ajax({
        type: "POST",
        url: "deletePost?id=" + POST_ID,
        success: function () {
            console.log(ITEM_ID + ' ' + TYPE_CATEGORY);
        }
    });
}