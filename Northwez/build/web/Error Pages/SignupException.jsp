<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="UTF-8" />
		<meta name="viewport" content="width=device-width,user-scalable=no" />
                <link rel="stylesheet" href="css/guest_style.css" />
		<link rel="stylesheet" href="css/aos.css" />
		<title>NORTHWEZ</title>
	</head>
	<body>  
            <div class="header_logo" data-aos="zoom-out">
                    <a href="#"><img src="css/images/logo.png" alt="logo" /></a>
            </div>
            <section class="container_1 cont">
                    <div class="text-container" id="text-cont">
                            <h1>Sign-up Exception<br /><span>There was an incorrect input in the text field within the sign-up form</span></h1>
                            <!-- (username goes here) -->
                    </div>
            </section>
            <div class="back-btn inactive" id="bck" onclick="hide()">BACK</div>
	</body>
	<script src="https://code.jquery.com/jquery-3.3.1.js"></script>
	<script src="js/aos.js"></script>
	<script>
		AOS.init({
			duration: 2000,
		});
	</script>
	<script>
		$(".btn-book").click(function () {
			$("#modal-container").removeClass("out").addClass("book");
			$("body").addClass("modal-active");
		});
		$("#back").click(function () {
			$("#modal-container").addClass("out");
			$("body").removeClass("modal-active");
		});

		function show() {
			var box1 = document.getElementById("box1");
			var text = document.getElementById("text-cont");
			var bck = document.getElementById("bck");
			if ($(".box-container").hasClass("inactive")) {
				box1.classList.remove("inactive");
				bck.classList.remove("inactive");
				$(".text").removeClass("inactive");
				text.classList.add("active");
				$("#modal-container").addClass("out");
				$("body").removeClass("modal-active");
			}
		}
		function hide() {
			var box1 = document.getElementById("box1");
			var text = document.getElementById("text-cont");
			var bck = document.getElementById("bck");
			if ($(".box-container").hasClass("inactive")) {
				console.log("yeet");
			} else {
				box1.classList.add("inactive");
				bck.classList.add("inactive");
				$(".text").addClass("inactive");
				text.classList.remove("active");
			}
		}
		function revert() {
			var cbox = document.getElementById("checkbox");
			cbox.checked = !cbox.checked;
		}
	</script>
</html>
