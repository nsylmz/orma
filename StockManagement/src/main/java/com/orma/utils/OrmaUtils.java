package com.orma.utils;

import java.util.List;

import com.orma.domain.IBaseEntity;

public class OrmaUtils {
	
	@SuppressWarnings("rawtypes")
	public static boolean listContains(List list, IBaseEntity baseEntity) {
		for (int i = 0; i < list.size(); i++) {
			if (baseEntity.contentEquals(list.get(i)))
				return true;
		}
		return false;
	}

}
