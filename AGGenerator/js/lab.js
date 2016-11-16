function change(name) {
	$('.navbar .item').removeClass('active');
	$('.content').removeClass('active');
	$('.' + name).addClass('active');
}