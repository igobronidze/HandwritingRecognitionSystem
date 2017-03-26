var fileList = $('#fileList');

function traverseFileStructure(obj, parentName) {
	var html = ''
	for(prop in obj) {
  	if(obj.hasOwnProperty(prop) && prop !== 'is_dir') {
    	if(obj[prop]['is_dir']) {
      	html += '<li><ul class="nested_dir"><span class="glyphicon glyphicon-chevron-up chevron-span"></span><input type="checkbox" value="" path="' + parentName + '/' + prop + '"><span class="glyphicon glyphicon-folder-close" style="color:#FDD835"></span>' + prop + traverseFileStructure(obj[prop], parentName + '/' + prop) + '</ul></li>';
      } else {
      	html += '<li><input type="checkbox" value=""><span class="glyphicon glyphicon-file" style="color:#B0BEC5"></span>' + prop + '</li>';
      }
    }
  }
  return html;
}

var arrDownClass = 'glyphicon-chevron-down'
var arrUpClass = 'glyphicon-chevron-up'

function chevronClickHandler(e) {
    var $this = $(this);
    $this.parent().children('li').toggle();
    if($this.hasClass(arrDownClass)) {
        $this.removeClass(arrDownClass);
        $this.addClass(arrUpClass);
    } else {
        $this.removeClass(arrUpClass);
        $this.addClass(arrDownClass);
    }
}

function checkboxClickHandler(e) {
    var $this = $(this);
    if($this.prop('checked')) {
        $this.parent().find('input[type="checkbox"]').prop('checked', true);
    } else {
        $this.parent().find('input[type="checkbox"]').prop('checked', false);
    }
}

$.post("/fs", function(data, status){
    if(status === "success") {
        fileList.append($.parseHTML('<ul><span class="glyphicon glyphicon-chevron-up chevron-span"></span><input type="checkbox" value="" path="/"><span class="glyphicon glyphicon-folder-close" style="color:#FDD835"></span>/' + traverseFileStructure(data.data[0], '') + '</ul>'));

        $('.chevron-span').click(chevronClickHandler);
        $('#fileList ul > .chevron-span').trigger('click');
        $('ul > input').click(checkboxClickHandler);
    }
});

$('.text-center tr').click(function(e) {
    var $this = $(this);
    var name = $this.find('.norm-name').text();
    var width = $this.find('.norm-width').text();
    var height = $this.find('.norm-height').text();
    var type = $this.find('.norm-type').text();
    $('.input-norm-name').val(name);
    $('.input-norm-width').val(width);
    $('.input-norm-height').val(height);
    $(".input-norm-type option:contains('" + type + "')").prop('selected', true);
});

function getAllCheckedFiles() {
    var allFiles = [];
    $('#fileList input[type="checkbox"]').each(function(i, el) {
        if(!($(el).prev().hasClass('chevron-span')) && $(el).prop("checked")) {
            allFiles.push($(el).parent().parent().find('input[type="checkbox"]').attr('path') + '/' +
                $(el).parent().clone()
                .children()
                .remove()
                .end()
                .text()
                .trim(' '));
        }
    });
    return allFiles;
}

function sendFileNames() {
    $.ajax({
        url: '/checkedFiles',
        type: 'POST',
        data: JSON.stringify({data: getAllCheckedFiles()}),
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        success: function(msg) {
            console.log(msg);
        }
    });
}

$('#norm-form').submit(function(){
    $('<input />').attr('type', 'hidden')
        .attr('name', "files")
        .attr('value', JSON.stringify({"data": getAllCheckedFiles()}))
        .appendTo('#norm-form');
    return true;
});