import { useRoute } from '@react-navigation/native';
import React from 'react';
import { View, Text, Image, TouchableOpacity } from 'react-native';
import { MenuTypeI } from './(tabs)/type';

const Detail = () => {
  const route = useRoute();
  const { recipe }: { recipe: string } = route.params as any;
  const parsedRecipe: MenuTypeI = JSON.parse(recipe);

  return (
    <View className="flex-1 bg-white">
      <Image className="w-full h-64" source={{ uri: parsedRecipe.photo }} />
      <View className="px-4 py-6 bg-white rounded-t-3xl -mt-6">
        <Text className="text-2xl font-bold">{parsedRecipe.name}</Text>
        <Text className="text-gray-500 mt-1">By Resto Parmato Bapo</Text>
        <View className="flex-row items-center mt-2">
          <Text className="text-yellow-500 text-base">⭐ 4.9</Text>
          <Text className="text-gray-500 ml-2 text-sm">20 min</Text>
        </View>
        <View className="flex-row items-center mt-4">
          <TouchableOpacity className="bg-gray-200 p-2 rounded-full">
            <Text className="text-xl">-</Text>
          </TouchableOpacity>
          <Text className="mx-4 text-xl">1</Text>
          <TouchableOpacity className="bg-gray-200 p-2 rounded-full">
            <Text className="text-xl">+</Text>
          </TouchableOpacity>
          <Text className="ml-auto text-2xl font-bold">Rs. {parsedRecipe.price}</Text>
        </View>
        <View className="mt-6">
          <Text className="text-lg font-bold">Description</Text>
          <Text className="text-gray-500 mt-2">{parsedRecipe.description}</Text>
        </View>
        <TouchableOpacity className="bg-orange-500 rounded-full p-4 mt-6">
          <Text className="text-white text-center font-bold">Add to Cart</Text>
        </TouchableOpacity>
      </View>
    </View>
  );
};

export default Detail;
