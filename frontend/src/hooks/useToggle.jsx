import { useState } from 'react';

const useToggle = (initial) => {
  const [isToggled, setToggled] = useState(initial);

  const changeToggle = () => {
    setToggled(!isToggled);
  }

  return [isToggled, changeToggle];
}

export default useToggle;