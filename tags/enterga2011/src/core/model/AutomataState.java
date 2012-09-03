package core.model;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public abstract class AutomataState implements Serializable {

	private static String nombreGrupo = "";

	private static final long serialVersionUID = 1764660909489565734L;

	private static HashMap <String, AutomataState> instances = new HashMap<String, AutomataState>();

	public abstract void execute(AutomataContainer master);

	public static AutomataState createState(String name, AutomataState currentState) {
		AutomataState nextState = null;
		String identificator = stateIdentificator(name);
		try{
			Class <? extends AutomataState> c = Class.forName(identificator).asSubclass(AutomataState.class);
			AutomataState as = instances.get(identificator);
			if (as != null)
				nextState = as;
			else{
				Constructor<?> parametrizedConstructor = c.getConstructor(AutomataState.class);
				nextState = (AutomataState) parametrizedConstructor.newInstance(currentState);
				instances.put(identificator,nextState);
			}
			if (currentState != null)
				nextState = copyAtributeValues(c,currentState,nextState);
		}catch (ClassNotFoundException e){

		}catch (IllegalAccessException e){

		}catch (InstantiationException e){

		}catch (NoSuchMethodException e){

		}catch (InvocationTargetException e){

		}
		return nextState;
	}

	private static AutomataState copyAtributeValues(Class <? extends AutomataState> stateClass, 
			AutomataState oldState, 
			AutomataState newState) {
		HashMap<String, Method> getterMethods = getGetterMethods(stateClass);
		Method getterMethod; 
		for (Field f:stateClass.getDeclaredFields()){
			getterMethod = getterMethods.get(f.getName());
			if (getterMethod != null){
				try {
					getterMethod.invoke(newState, f.get(oldState));
				} catch (Exception e) {
					System.out.println(String.format("Error copying AutomataState [class:%s][attribute:%s][method:%s] %s",
							stateClass.getName(),
							f.getName(),
							getterMethod.getName(),
							e.getMessage()));
					e.printStackTrace();
				} 
			}
		}
		return newState;
	}

	private static HashMap <String, Method> getGetterMethods(Class <? extends AutomataState> stateClass){
		HashMap<String, Method> methods = new HashMap<String, Method>();
		ArrayList<Method> allMethods = new ArrayList<Method>(Arrays.asList(stateClass.getDeclaredMethods()));
		String variableName;
		for (Method m:allMethods){
			if (m.getName().startsWith("get")){
				variableName = m.getName().substring(3, 4).toLowerCase();
				variableName = variableName.concat(m.getName().substring(4));
				methods.put(variableName,m);
			}
		}
		return methods;
	}

	private static String stateIdentificator(String stateName){
		return nombreGrupo+"."+stateName;
	}

}