import './style.css'

document.querySelectorAll('.gallery').forEach((gallery) => {
  const track = gallery.querySelector('.gallery-track');
  const step = () => track.clientWidth;

  gallery.querySelector('.gallery-next').addEventListener('click', () => {
    track.scrollBy({left: step(), behavior: 'smooth'});
  });

  gallery.querySelector('.gallery-prev').addEventListener('click', () => {
    track.scrollBy({left: -step(), behavior: 'smooth'});
  });
});