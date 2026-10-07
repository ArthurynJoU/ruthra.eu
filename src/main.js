import './style.css'
import items from './data/projects.json'

function renderGallery(images) {
  const slides = images
    .map(
      (image) =>
        `<img src="${image.src}" alt="${image.alt}" width="1200" height="800" loading="lazy" />`,
    )
    .join('');

  return `
    <div class="gallery">
      <button class="gallery-prev" type="button" aria-label="Previous image">
        <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round" aria-hidden="true"><polyline points="15 18 9 12 15 6"></polyline></svg>
      </button>
      <button class="gallery-next" type="button" aria-label="Next image">
        <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round" aria-hidden="true"><polyline points="9 18 15 12 9 6"></polyline></svg>
      </button>
      <div class="gallery-track">${slides}</div>
    </div>
  `;
}

function renderCard(item) {
  const tagItems = item.tags.map((tag) => `<li>${tag}</li>`).join('');
  const tags = item.tags.length > 0 ? `<ul class="tags">${tagItems}</ul>` : '';

  const link = item.link ? `<a href="${item.link}">${item.linkText}</a>` : '';

  const first = item.images[0];
  const media =
    item.images.length > 1
      ? renderGallery(item.images)
      : `<img src="${first.src}" alt="${first.alt}" width="1200" height="800" loading="lazy" />`;

  return `
    <article class="project-card">
      ${media}
      <h3>${item.title}</h3>
      <p>${item.description}</p>
      ${tags}
      ${link}
    </article>
  `;
}

function renderSection(section) {
  const container = document.querySelector(`#${section} .projects`);
  container.innerHTML = items
    .filter((item) => item.section === section)
    .map(renderCard)
    .join('');
}

renderSection('projects');
renderSection('achievements');
renderSection('education');
initGalleries();

function initGalleries() {
  document.querySelectorAll('.gallery').forEach((gallery) => {
    const track = gallery.querySelector('.gallery-track');
    const step = () => track.clientWidth;

    gallery.querySelector('.gallery-next').addEventListener('click', () => {
      track.scrollBy({ left: step(), behavior: 'smooth' });
    });

    gallery.querySelector('.gallery-prev').addEventListener('click', () => {
      track.scrollBy({ left: -step(), behavior: 'smooth' });
    });
  });
}


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