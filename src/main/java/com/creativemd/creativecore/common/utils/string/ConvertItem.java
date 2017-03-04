package com.creativemd.creativecore.common.utils.string;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class ConvertItem extends StringConverter{

	public ConvertItem() {
		super("Item");
	}

	@Override
	public Class getClassOfObject() {
		return Item.class;
	}

	@Override
	public String toString(Object object) {
		ResourceLocation resource = Item.REGISTRY.getNameForObject((Item) object);
		if(resource != null)
			return resource.toString();
		return null;
	}

	@Override
	public Object parseObject(String input) {
		return Item.REGISTRY.getObject(new ResourceLocation(input));
	}

	@Override
	public String[] getSplitter() {
		return new String[0];
	}

}