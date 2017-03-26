var fileList = $('#fileList');

function traverseFileStructure(obj) {
	var html = ''
	for(prop in obj) {
  	if(obj.hasOwnProperty(prop) && prop !== 'is_dir') {
    	if(obj[prop]['is_dir']) {
      	html += '<li><ul class="nested_dir"><span class="glyphicon glyphicon-chevron-up chevron-span"></span><input type="checkbox" value=""><span class="glyphicon glyphicon-folder-close" style="color:#FDD835"></span>' + prop + traverseFileStructure(obj[prop]) + '</ul></li>';
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
        fileList.append($.parseHTML('<ul><span class="glyphicon glyphicon-chevron-up chevron-span"></span><input type="checkbox" value=""><span class="glyphicon glyphicon-folder-close" style="color:#FDD835"></span>root' + traverseFileStructure(data.data[0]) + '</ul>'));

        $('.chevron-span').click(chevronClickHandler);
        $('#fileList ul > .chevron-span').trigger('click');
        $('ul > input').click(checkboxClickHandler);
    }
});