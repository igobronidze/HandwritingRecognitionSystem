// $("input[type='image']").click(function() {
//     window.alert("rame");
//     $("input[id='my_file']").click();
// });

function readURL(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();
        reader.onload = function (e) {
            $(".image-preview").attr('src', e.target.result);
        }
        reader.readAsDataURL(input.files[0]);
    }
}

$(".image-preview").click(function (e) {
    var my_file = $('#my_file');
    my_file.click();
});

$("#my_file").change(function () {
    readURL(this);
});

$('form').on('submit', function (e) {
    e.preventDefault();
    var formData = new FormData(this);
    // $('#spinner').addClass('loader');
    $.ajax({
        type: 'POST',
        url: $(this).attr('action'),
        data: formData,
        cache: false,
        contentType: false,
        processData: false,
        success: function (data) {
            console.log("success");
            console.log(data);
            // $('#spinner').removeClass('loader');
            $('.result-textarea').text(data);
        },
        error: function (data) {
            console.log("error");
            console.log(data);
        }
    });
});