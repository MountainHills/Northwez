<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="UTF-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link rel="stylesheet" href="css/gallery_style.css" />
		<link rel="stylesheet" href="css/aos.css" />
		<title>NORTHWEZ</title>
	</head>
	<body>
		<script src="https://code.jquery.com/jquery-3.3.1.js"></script>
		<script src="https://unpkg.com/imagesloaded@4/imagesloaded.pkgd.min.js"></script>
		<script src="https://unpkg.com/masonry-layout@4/dist/masonry.pkgd.min.js"></script>

		<div class="header_logo" data-aos="zoom-out" id="header_logo">
			<a><img src="css/images/logo.png" alt="logo" /></a>
		</div>

		<section class="text" id="text">
			<div class="container">
				<div class="logo" data-aos="zoom-out-down" data-aos-delay="500">
					<h1 data-aos="zoom-in">NORTHWEZ</h1>
					<h2>GALLERY</h2>
					<a href="#" onclick="goBack()">BACK</a>
				</div>
			</div>
		</section>

		<section class="grid-section">
			<div class="grid">
				<div class="grid-item g1" data-aos="zoom-in-down">
					<img src="css/images/img5.jpg" />
				</div>

				<div
					class="grid-item g2"
					data-aos="fade-down"
					data-aos-anchor=".g1"
					data-aos-delay="400"
				>
					<img src="css/images/img14.jpg" />
				</div>

				<div
					class="grid-item g3"
					data-aos="fade-left"
					data-aos-anchor=".g1"
					data-aos-delay="600"
				>
					<img src="css/images/img4.jpg" />
				</div>

				<div
					class="grid-item g4"
					data-aos="zoom-in-left"
					data-aos-delay="300"
					data-aos-anchor=".g1"
					data-aos-anchor-placement="top-top"
				>
					<img src="css/images/img3.jpg" />
				</div>

				<div
					class="grid-item g5"
					data-aos="slide-up"
					data-aos-anchor=".g1"
					data-aos-delay="600"
					data-aos-anchor-placement="top-top"
				>
					<img src="css/images/img1.jpg" />
				</div>

				<div
					class="grid-item g6"
					data-aos="fade-left"
					data-aos-anchor=".g1"
					data-aos-delay="1000"
					data-aos-anchor-placement="center-bottom"
				>
					<img src="css/images/img11.jpg" />
				</div>

				<div
					class="grid-item g7"
					data-aos="slide-up"
					data-aos-anchor=".g2"
					data-aos-delay="600"
					data-aos-anchor-placement="top-center"
				>
					<img src="css/images/img2.jpg" />
				</div>

				<div
					class="grid-item g8"
					data-aos="zoom-in"
					data-aos-anchor=".g3"
					data-aos-anchor-placement="center-bottom"
				>
					<img src="css/images/img9.jpg" />
				</div>

				<div
					class="grid-item g9"
					data-aos="zoom-in-right"
					data-aos-anchor=".g3"
					data-aos-anchor-placement="top-center"
				>
					<img src="css/images/img12.jpg" />
				</div>

				<div
					class="grid-item"
					data-aos="zoom-in"
					data-aos-anchor=".g2"
					data-aos-delay="300"
					data-aos-anchor-placement="center-top"
				>
					<img src="css/images/img13.jpg" />
				</div>

				<div
					class="grid-item"
					data-aos="slide-right"
					data-aos-anchor=".g3"
					data-aos-delay="400"
					data-aos-anchor-placement="top-center"
				>
					<img src="css/images/img6.jpg" />
				</div>

				<div
					class="grid-item"
					data-aos="fade-up"
					data-aos-anchor=".g3"
					data-aos-delay="600"
					data-aos-anchor-placement="top-top"
				>
					<img src="css/images/img10.jpg" />
				</div>
			</div>
		</section>
		<div onclick="scrollToTop()" class="arrow animated"></div>
	</body>
	<script>
		$(".grid").imagesLoaded(function () {
			$(".grid").masonry({
				itemSelector: ".grid-item",
			});
		});
	</script>
	<script>
		function goBack() {
			window.history.back();
		}
		function scrollToTop() {
			window.scrollTo({ top: 0, behavior: "smooth" });
		}
	</script>
	<script src="js/aos.js"></script>
	<script>
		AOS.init({
			duration: 2000,
		});
	</script>
	<script>
		window.onunload = function () {
			window.scrollTo(0, 0);
		};
	</script>
</html>
