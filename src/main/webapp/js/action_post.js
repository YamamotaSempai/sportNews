$(function () {
    $('#card__button-btn').click(function () {
        deletePost(this);
    });
});

function deletePost(caller) {
    var POST_ID = caller.getAttribute('post_id');

    $.ajax({
        type: "POST",
        url: "deletePost?id=" + POST_ID,
        success: function () {
            console.log(ITEM_ID + ' ' + TYPE_CATEGORY);
            alert("Удален пост с ID " + POST_ID);
            location.reload();
        }
    });
}