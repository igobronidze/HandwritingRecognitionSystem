$('#new-button').click(function() {
    $('#textinput-key').val('');
    $('#textinput-key').prop('readonly', false);
    $('#textinput-value').val('');
    $("#select-type").val($("#select-type option:first").val());
    $('#hiddeninput-action').val('save');
});

$('#delete-button').click(function() {
    $('#hiddeninput-action').val('delete');
});

$('#param-table tbody tr').click(function() {
   var el = $(this);
   $('#textinput-key').val(el.find('.param-key').text());
   $('#textinput-key').prop('readonly', true);
   $('#textinput-value').val(el.find('.param-value').text());
   $('#select-type').val(el.find('.param-type').text());
   $('#hiddeninput-action').val('edit');
});