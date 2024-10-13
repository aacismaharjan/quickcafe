import React, { useState, useEffect } from 'react';
import { View, Text, FlatList, TouchableOpacity, StyleSheet, Image, ScrollView } from 'react-native';
import { StackNavigationProp } from '@react-navigation/stack';
import { RouteProp } from '@react-navigation/native';
import { MenuTypeI } from './type';
import { useNavigation, useRouter } from 'expo-router';
import menuDatas from "./../../constants/Menus"

type Props = {
};

const HomeScreen: React.FC<Props> = () => {
  const router = useRouter();
  const [menus, setMenus] = useState<MenuTypeI[]>([]);

  useEffect(() => {
    setMenus(menuDatas)
  }, []);

  return (
    <View>
      <ScrollView>
        <View className='mt-4'>
          <View className='flex-row justify-between items-center px-4'>
            <View>
              <Text className='font-bold text-lg'>Hot and Spicy</Text>
              <Text className='text-gray-500 text-xs'>soft and delicious cooked momos</Text>
            </View>

            <TouchableOpacity>
              <Text>See All</Text>
            </TouchableOpacity>
          </View>

          <ScrollView className="overflow-visible flex gap-4 py-5" horizontal showsHorizontalScrollIndicator={false} contentContainerStyle={{
            paddingHorizontal: 15
          }}>
            {menus.map(menu => <TouchableOpacity
              className='relative rounded-3xl bg-white overflow-hidden'
              onPress={() => router.push({ pathname: '/detail', params: { recipe: JSON.stringify(menu) } })}
            >
              <Image className="w-[220] h-[220] object-fit" source={{ uri: menu.photo || "https://plantoeat.s3.amazonaws.com/menus/16687602/ba918c2550fd60a3f56da6cc510eb24b56752f63-original.jpg?1535321822" }} />

              <View className='p-4'>
                <Text className="font-bold mb-1 text-base" >{menu.name}</Text>
                <Text className="font-bold text-base" >Rs. {menu.price}</Text>
              </View>
            </TouchableOpacity>)}
          </ScrollView>
        </View>

        <View className='mt-4'>
          <View className='flex-row justify-between items-center px-4'>
            <View>
              <Text className='font-bold text-lg'>Hot and Spicy</Text>
              <Text className='text-gray-500 text-xs'>soft and delicious cooked momos</Text>
            </View>

            <TouchableOpacity>
              <Text>See All</Text>
            </TouchableOpacity>
          </View>

          <ScrollView className="overflow-visible flex gap-4 py-5" horizontal showsHorizontalScrollIndicator={false} contentContainerStyle={{
            paddingHorizontal: 15
          }}>
            {menus.map(menu => <TouchableOpacity
              className='relative rounded-3xl bg-white overflow-hidden'
              onPress={() => router.push({ pathname: '/detail', params: { recipe: JSON.stringify(menu) } })}
            >
              <Image className="w-[220] h-[220] object-fit" source={{ uri: menu.photo || "https://plantoeat.s3.amazonaws.com/menus/16687602/ba918c2550fd60a3f56da6cc510eb24b56752f63-original.jpg?1535321822" }} />

              <View className='p-4'>
                <Text className="font-bold mb-1 text-base" >{menu.name}</Text>
                <Text className="font-bold text-base" >Rs. {menu.price}</Text>
              </View>
            </TouchableOpacity>)}
          </ScrollView>
        </View>
      </ScrollView>
    </View>
  );
};



export default HomeScreen;
