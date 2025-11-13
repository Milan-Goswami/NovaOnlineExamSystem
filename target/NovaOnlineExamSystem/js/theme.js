/* Beginner note: This file remembers the chosen theme (light or dark) and handles the mobile menu toggle. */
const THEME_KEY = 'nova-theme-preference';

function applyTheme(mode) {
  const body = document.body;
  const isDark = mode === 'dark';
  body.classList.toggle('dark', isDark);
  return isDark;
}

function getStoredTheme() {
  const saved = window.localStorage.getItem(THEME_KEY);
  if (saved === 'light' || saved === 'dark') {
    return saved;
  }
  return window.matchMedia && window.matchMedia('(prefers-color-scheme: dark)').matches ? 'dark' : 'light';
}

function setTheme(mode) {
  const next = mode === 'dark' ? 'dark' : 'light';
  applyTheme(next);
  window.localStorage.setItem(THEME_KEY, next);
  return next;
}

function updateThemeButtons(isDark) {
  document.querySelectorAll('[data-theme-toggle]').forEach((button) => {
    button.setAttribute('aria-pressed', isDark);
  });
}

function toggleTheme() {
  const current = document.body.classList.contains('dark') ? 'dark' : 'light';
  const next = current === 'dark' ? 'light' : 'dark';
  const applied = setTheme(next);
  updateThemeButtons(applied === 'dark');
}

function initThemeButtons() {
  const initial = getStoredTheme();
  const isDark = setTheme(initial) === 'dark';
  updateThemeButtons(isDark);
  document.querySelectorAll('[data-theme-toggle]').forEach((button) => {
    button.addEventListener('click', toggleTheme);
  });
}

function initNavToggle() {
  document.querySelectorAll('[data-nav-toggle]').forEach((toggle) => {
    const menu = toggle.closest('.site-nav')?.querySelector('[data-nav-menu]');
    if (!menu) return;

    toggle.addEventListener('click', () => {
      const expanded = toggle.getAttribute('aria-expanded') === 'true';
      const next = !expanded;
      toggle.setAttribute('aria-expanded', String(next));
      menu.setAttribute('data-open', String(next));
    });
  });
}

document.addEventListener('DOMContentLoaded', () => {
  initThemeButtons();
  initNavToggle();

  if (window.matchMedia) {
    const mediaQuery = window.matchMedia('(prefers-color-scheme: dark)');
    const syncTheme = (event) => {
      if (!window.localStorage.getItem(THEME_KEY)) {
        setTheme(event.matches ? 'dark' : 'light');
      }
    };
    if (typeof mediaQuery.addEventListener === 'function') {
      mediaQuery.addEventListener('change', syncTheme);
    } else if (typeof mediaQuery.addListener === 'function') {
      mediaQuery.addListener(syncTheme);
    }
  }
});
