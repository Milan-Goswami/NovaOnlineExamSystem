/* Beginner note: This script powers the circular countdown clock and submits the form when time is up. */
const TIMER_CIRCUMFERENCE = 2 * Math.PI * 54; // Radius from SVG circle (r=54)

function formatTime(seconds) {
  const mins = Math.floor(seconds / 60);
  const secs = seconds % 60;
  return `${String(mins).padStart(2, '0')}:${String(secs).padStart(2, '0')}`;
}

function updateTimerUI(state) {
  if (!state.textEl) {
    return;
  }

  state.textEl.textContent = formatTime(Math.max(0, state.remaining));

  if (state.progressEl && state.duration > 0) {
    const ratio = Math.max(0, state.remaining) / state.duration;
    state.progressEl.style.strokeDasharray = TIMER_CIRCUMFERENCE;
    state.progressEl.style.strokeDashoffset = TIMER_CIRCUMFERENCE * (1 - ratio);

    if (state.remaining <= 20) {
      state.progressEl.style.stroke = '#ef4444';
    } else if (state.remaining <= 60) {
      state.progressEl.style.stroke = '#f97316';
    } else {
      state.progressEl.style.stroke = '';
    }
  }
}

function autoSubmit(form) {
  if (form && typeof form.submit === 'function') {
    form.submit();
  }
}

function startTimer(container) {
  const duration = parseInt(container.getAttribute('data-duration'), 10);
  if (Number.isNaN(duration) || duration <= 0) {
    return;
  }

  const formId = container.getAttribute('data-form');
  const form = formId ? document.getElementById(formId) : container.closest('form');
  const textEl = container.querySelector('[data-timer-text]');
  const progressEl = container.querySelector('.timer-ring__progress');

  const state = {
    container,
    form,
    textEl,
    progressEl,
    duration,
    remaining: duration,
    intervalId: null,
  };

  updateTimerUI(state);

  state.intervalId = window.setInterval(() => {
    state.remaining -= 1;
    updateTimerUI(state);

    if (state.remaining <= 0) {
      window.clearInterval(state.intervalId);
      autoSubmit(state.form);
    }
  }, 1000);

  container._timerState = state;
}

document.addEventListener('DOMContentLoaded', () => {
  document.querySelectorAll('[data-timer]').forEach(startTimer);
});
