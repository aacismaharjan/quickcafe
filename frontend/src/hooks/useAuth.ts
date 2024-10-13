import { useSelector } from 'react-redux';
import { RootState } from '../store/store';

const useAuth = () => {
  const { isLoggedIn, token } = useSelector((state: RootState) => state.auth);
  return { isLoggedIn, token };
};

export default useAuth;
