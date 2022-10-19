import java.io.*;
import java.lang.reflect.Method;
class FunctionHandler extends ClassLoader
{
	Class<?> cls;
	
	
	public String exec(String function,String arg)
	{
		try{
			Class[] paramtypes={String.class};
			Object[] args ={arg};
			return (String)((cls.getMethod(function,paramtypes)).invoke(null,args));
		}catch(Exception e)
		{
			e.printStackTrace();
			//System.out.println("\""+function+"\"" + " not found");
			return "";
		}	
	}
	//idk for now
	public Class reload() throws FileNotFoundException,IOException,ClassNotFoundException
	{
		if(cls==null)
		{
			File file=new File("Functions.class");
			FileInputStream is=new FileInputStream(file);
			byte[] data=new byte[(int)file.length()];
			is.read(data);
			cls = defineClass("Functions",data,0,data.length);
			resolveClass(cls);
			//Method[] methods = cls.getMethods();
			//for(int i=0;i<methods.length;i++)
				//System.out.println(methods[i]);
		}
		else
			cls=new FunctionHandler(false).reload();
			
		return cls;
	}
	public void reset() throws ClassNotFoundException
	{
		cls=Class.forName("Functions");
	}
	public FunctionHandler(boolean dflt)throws ClassNotFoundException
	{
		super();
		if(dflt)
			cls=Class.forName("Functions");
		else
			cls=null;
	}
	
}