$(document).ready(function () {

    $('#send_email').click(function (event) {

        event.preventDefault();
        var CONTEXT = $("#input_context").val();
        var NAME = $("#input_name").val();
        var TITLE = $("#input_title").val();

        $.ajax({

            type: "POST",
            url: "/sendEmail",
            data: {
                name: NAME,
                title: TITLE,
                context: CONTEXT
            },
            success: function (data) {

                if (data.status === 'FAIL') {
                    showFormError(data.errorMessageList);
                } else {
                    $('#modalContactForm').hide();
                }
            },
            error: function (ex) {
                console.log(ex);
            }
        });
    });

    function showFormError(errorVal) {
        for (var i = 0; i < errorVal.length; i++) {
            EmailField.attr("placeholder", errorVal[i].message).css("border", " 1px solid red");
        }
    }
});