/*
 *   Copyright (C) 2012 John Hostile
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

import java.lang.reflect.Method;
import android.util.DisplayMetrics;
import android.view.Display;

public class SudoDisplayMetrics {
	
	@SuppressWarnings("unused")
	private static SudoDisplayMetrics instance = new SudoDisplayMetrics();	
	private static Method mGetRealDisplayMetricsMethod;
	
	private SudoDisplayMetrics(){
		try{
			@SuppressWarnings("rawtypes")
			Class displayClass = Class.forName("android.view.Display");
			Method[] methodArray = displayClass.getMethods();
			for(Method crystal:methodArray){
				if(crystal.getName().equals("getRealMetrics")){
					mGetRealDisplayMetricsMethod = crystal;
					break;
				}
			}
		}catch(Exception e){
			mGetRealDisplayMetricsMethod=null;
		}
	}
	
	public static void getRealDisplayMetrics(Display display, DisplayMetrics displayMetrics){
		if(mGetRealDisplayMetricsMethod==null)
			display.getMetrics(displayMetrics);
		try{
			Object[] args = new Object[]{displayMetrics};
			mGetRealDisplayMetricsMethod.invoke(display, args);
		}catch(Exception e){
			display.getMetrics(displayMetrics);
		}		
	}
	
}