/** @type {import('tailwindcss').Config} */
export default {
  content: ['./index.html', './src/**/*.{js,ts,jsx,tsx}'],
  theme: {
    extend: {
      colors: {
        primary: {
          DEFAULT: '#6C4CDB',
          50: '#F2EEFC',
          100: '#E4DBFA',
          200: '#C9B7F4',
          300: '#AD92EE',
          400: '#916EE8',
          500: '#6C4CDB',
          600: '#5A3DBE',
          700: '#48309A',
          800: '#362477',
          900: '#241853',
        },
        secondary: {
          DEFAULT: '#3FB68A',
          50: '#EAFBF4',
          100: '#CFF5E4',
          200: '#9FEAC9',
          300: '#6FDFAE',
          400: '#53CB9A',
          500: '#3FB68A',
          600: '#319571',
          700: '#257259',
          800: '#194F3E',
          900: '#0D2C24',
        },
        background: '#FBFAFF',
      },
      borderRadius: {
        xl: '16px',
        '2xl': '20px',
        '3xl': '24px',
      },
    },
  },
  plugins: [],
};
