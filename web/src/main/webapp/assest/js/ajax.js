/**
 * Created by Sergey_Shmouradko on 5/15/2017.
 */
$(document).on('submit', '#register-form', function(e) {
    var frm = $('#register-form');
    var name = $('#name').val();
    var surname = $('#surname').val();
    var email = $('#email').val();
    var login = $('#login').val();
    var password = $('#password').val();
    var data = "name=" + name + "&surname=" + surname + "&email=" + email + "&login=" + login + "&password=" + password;

    if(frm.valid()) {
        $.ajax({
            contentType: "text/html; charset=UTF-8",
            dataType: "text/javascript",
            type: frm.attr('method'),
            url: frm.attr('action'),
            data: data,
            success: function(response){
                // // we have the response
                // $('#info').html(response);
                // $('#name').val('');
                // $('#surname').val('');
                // $('#email').val('');
                // $('#login').val('');
                // $('#password').val('');
            },
            error: function(e){
                alert('Error: ' + e);
            }
        });
    }
});