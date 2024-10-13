import React from 'react';
import Button from '../components/atoms/button/Button';

export const AboutPage: React.FC = () => {
  const handleClick = () => {
    console.log('Button clicked');
  };

  return (
    <div>
      <h1>Welcome to the Home Page</h1>
      <Button label="Click Me" onClick={handleClick} />
    </div>
  );
};
