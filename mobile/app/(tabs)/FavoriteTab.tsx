import React, { useState, useEffect } from 'react';
import { View, Text, FlatList, TouchableOpacity, StyleSheet, Image, ScrollView } from 'react-native';
import { StackNavigationProp } from '@react-navigation/stack';
import { RouteProp } from '@react-navigation/native';
import { MenuTypeI } from './type';
import { useNavigation, useRouter } from 'expo-router';
import menuDatas from "./../../constants/Menus"
import { AntDesign } from '@expo/vector-icons';

type Props = {};

const FavoriteTab: React.FC<Props> = () => {
  const router = useRouter();
  const [menus, setMenus] = useState<MenuTypeI[]>([]);
  const [favorites, setFavorites] = useState<Set<string>>(new Set()); // Use Set for unique favorites

  useEffect(() => {
    setMenus(menuDatas);
  }, []);

  const toggleFavorite = (menuId: string) => {
    setFavorites((prevFavorites) => {
      const newFavorites = new Set(prevFavorites);
      if (newFavorites.has(menuId)) {
        newFavorites.delete(menuId);
      } else {
        newFavorites.add(menuId);
      }
      return newFavorites;
    });
  };

  return (
    <View className='flex-1 p-3'>
      <ScrollView contentContainerStyle={{ paddingHorizontal: 8 }}>
        <View className='flex flex-row flex-wrap -m-2'>
          {menus.map(menu => (
            <TouchableOpacity
              key={menu.id} // Ensure each item has a unique key
              className='w-1/2 p-2' // Use w-1/2 to set width to 50% for 2 columns
              onPress={() => router.push({ pathname: '/detail', params: { recipe: JSON.stringify(menu) } })}
            >
              <View className='rounded-3xl bg-white overflow-hidden'>
                <Image
                  className='w-full h-52 object-cover' 
                  source={{ uri: menu.photo || "https://plantoeat.s3.amazonaws.com/menus/16687602/ba918c2550fd60a3f56da6cc510eb24b56752f63-original.jpg?1535321822" }}
                />
                <View className='p-4 flex flex-row items-center justify-between'>
                  <View className='flex-1'>
                    <Text className='font-bold text-base'>{menu.name}</Text>
                    <View className='flex flex-row items-center justify-between'>
                      <Text className='font-bold text-base'>Rs. {menu.price}</Text>
                      <TouchableOpacity onPress={() => toggleFavorite(menu.id.toString())} className='ml-2'>
                        <AntDesign
                          name={favorites.has(menu.id.toString()) ? 'heart' : 'hearto'}
                          size={28}
                          color={favorites.has(menu.id.toString()) ? 'red' : 'gray'}
                        />
                      </TouchableOpacity>
                    </View>
                  </View>
                </View>
              </View>
            </TouchableOpacity>
          ))}
        </View>
      </ScrollView>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flexDirection: 'row',
    flexWrap: 'wrap',
    padding: 16,
  },
  item: {
    gap: 10,
  },
});


export default FavoriteTab;
