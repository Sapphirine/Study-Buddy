// JavaScript Document
var user = document.getElementById("username");
var password = document.getElementById("password");
function check_login(){
	var isValidate = false;
	if (!user.value.match(/^\S(2,20)$/)){
		user.className = 'userRed';
		user.focus();
		return;
		}
		else
		{
		user.className = 'text';
		isValidata = true;
	}
	
	if (password.value.length<3 || password.value.length>20){
		password.className = 'userRed';
		password.focus();
		return;
		}
		else {
		password.className = 'text';
		isValidate = true;
	}
	
	if (isValidate) {
		var ajax = Ajax();
		ajax.get('login.php?user='+document.getElementById('user').value+'&password='+document.getElementById('password').value, function(data){
			var con = document.getElementById("con");
			eval(data);
			if (login){
				con.innerHTML = '<font color = "green">LOGINING IN...</font>';
				window.open("index.html","_self");
				}
			else {
				con.innerHTML = '<font color = "red">ERROR!</font>';
				}
		});
	}	
}
		