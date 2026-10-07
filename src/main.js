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

const toggle = document.querySelector('.menu-toggle');
const menu = document.querySelector('#site-menu');

toggle.addEventListener('click', () => {
  const isOpen = toggle.getAttribute('aria-expanded') === 'true';
  toggle.setAttribute('aria-expanded', String(!isOpen));

  menu.classList.toggle('is-open');
});

menu.addEventListener('click', () => {
  const isOpen = toggle.getAttribute('aria-expanded') === 'false';
  toggle.setAttribute('aria-expanded', String(!isOpen));

  menu.classList.toggle('is-open');
});

const form = document.querySelector('form');

form.addEventListener('submit', (event) => {
  event.preventDefault();

  if (!form.checkValidity()) {
    form.reportValidity();
    return;
  }

  const data = Object.fromEntries(new FormData(form));
  console.log(data);
});