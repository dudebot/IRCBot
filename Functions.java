	/*
		this class contains all of the methods that are used by the IRCBot and
		can be dynamically reloaded
		all methods defined here must return a string and have one string as an input.
		this is because there is text input from the user, and text output the user wants
	*/
import java.util.Scanner;
import java.util.ArrayList;
import net.turkeh.tribes2.Server;
//import java.io.IOException;
import java.io.*;
import java.net.*;
import java.lang.Math;
import java.util.*;

import org.nfunk.jep.*;
import org.nfunk.jep.type.*;
import org.nfunk.jep.function.*;

class Factorial extends PostfixMathCommand
{
	public Factorial()
	{
		numberOfParameters = 1;
	}
	
	public void run(Stack inStack)
		throws ParseException 
	{
		checkStack(inStack);// check the stack
		Object param = inStack.pop();
		inStack.push(fact(param));//push the result on the inStack
		return;
	}

	public Object fact(Object param)
		throws ParseException
	{
		double j=Math.floor(((Number)param).doubleValue());
		double acc=0;
		while(j>1)
		{
			acc*=j;
			j--;
		}
		return new Double(acc);

		
		//throw new ParseException("Invalid parameter type");
	}
}


public class Functions
{
	//this makes the reflection a bit less messy in IRCBot.java
	static String version = "0.7";
	static String[] fortunes = {"\"Welcome\" is a powerful word.","A dubious friend may be an enemy in camouflage.","A feather in the hand is better than a bird in the air. ","A fresh start will put you on your way.","A friend asks only for your time not your money.","A friend is a present you give yourself.","A gambler not only will lose what he has, but also will lose what he doesn't have.","A golden egg of opportunity falls into your lap this month.","A good time to finish up old tasks. ","A hunch is creativity trying to tell you something.","A light heart carries you through all the hard times.","A new perspective will come with the new year. ","A person is never to (sic) old to learn. ","A person of words and not deeds is like a garden full of weeds.","A pleasant surprise is waiting for you.","A smile is your personal welcome mat.","A smooth long journey! Great expectations.","A soft voice may be awfully persuasive.","A truly rich life contains love and art in abundance.","Accept something that you cannot change, and you will feel better.","Adventure can be real happiness.","Advice is like kissing. It costs nothing and is a pleasant thing to do.","Advice, when most needed, is least heeded.","All the effort you are making will ultimately pay off.","All the troubles you have will pass away very quickly.","All will go well with your new project.","All your hard work will soon pay off.","Allow compassion to guide your decisions.","An agreeable romance might begin to take on the appearance.","An important person will offer you support.","An inch of time is an inch of gold.","Be careful or you could fall for some tricks today.","Beauty in its various forms appeals to you. ","Because you demand more from yourself, others respect you deeply.","Believe in yourself and others will too.","Believe it can be done.","Better ask twice than lose yourself once.","Carve your name on your heart and not on marble.","Change is happening in your life, so go with the flow!","Competence like yours is underrated.","Congratulations! You are on your way.","Could I get some directions to your heart? ","Courtesy begins in the home.","Courtesy is contagious.","Curiosity kills boredom. Nothing can kill curiosity.","Dedicate yourself with a calm mind to the task at hand.","Depart not from the path which fate has you assigned.","Determination is what you need now.","Disbelief destroys the magic.","Distance yourself from the vain.","Do not be intimidated by the eloquence of others.","Do not let ambitions overshadow small success.","Do not make extra work for yourself.","Do not underestimate yourself. Human beings have unlimited potentials.","Don't be discouraged, because every wrong attempt discarded is another step forward.","Don't confuse recklessness with confidence. ","Don't just spend time. Invest it.","Don't just think, act!","Don't let friends impose on you, work calmly and silently.","Don't let the past and useless detail choke your existence.","Don't let your limitations overshadow your talents.","Don't worry; prosperity will knock on your door soon.","Each day, compel yourself to do something you would rather not do.","Education is the ability to meet life's situations.","Emulate what you admire in your parents. ","Emulate what you respect in your friends.","Every flower blooms in its own sweet time.","Every wise man started out by asking many questions.","Everyday in your life is a special occasion.","Failure is the chance to do better next time.","Feeding a cow with roses does not get extra appreciation.","For hate is never conquered by hate. Hate is conquered by love.","Fortune Not Found: Abort, Retry, Ignore?","From listening comes wisdom and from speaking repentance.","From now on your kindness will lead you to success.","Get your mind set...confidence will lead you on.","Go take a rest; you deserve it.","Good news will be brought to you by mail.","Good news will come to you by mail.","Good to begin well, better to end well.","Happiness begins with facing life with a smile and a wink.","Happiness will bring you good luck.","Happy life is just in front of you.","Hard words break no bones, fine words butter no parsnips.","Have a beautiful day.","He who expects no gratitude shall never be disappointed. ","He who knows he has enough is rich.","Help! I'm being held prisoner in a chinese bakery!","How you look depends on where you go.","I learn by going where I have to go.","If a true sense of value is to be yours it must come through service.","If certainty were truth, we would never be wrong.","If you continually give, you will continually have.","If you look in the right places, you can find some good offerings.","If you think you can do a thing or think you can't do a thing, you're right.","If your desires are not extravagant, they will be granted.","If your desires are not to extravagant they will be granted. ","In order to take, one must first give.","In the end all things will be known.","It could be better, but its[sic] good enough.","It is better to deal with problems before they arise.","It is honorable to stand up for what is right, however unpopular it seems.","It is worth reviewing some old lessons.","It takes courage to admit fault.","It's time to get moving. Your spirits will lift accordingly.","Keep your face to the sunshine and you will never see shadows.","Let the world be filled with tranquility and goodwill.","Listen not to vain words of empty tongue.","Listen to everyone. Ideas come from everywhere.","Living with a commitment to excellence shall take you far.","Love is a warm fire to keep the soul warm.","Love is like sweet medicine, good to the last drop.","Love lights up the world.","Love truth, but pardon error. ","Man is born to live and not prepared to live.","Many will travel to hear you speak.","Meditation with an old enemy is advised.","Miles are covered one step at a time.","Nature, time and patience are the three great physicians.","Never fear! The end of something marks the start of something new.","New ideas could be profitable.","New people will bring you new realizations, especially about big issues. ","No one can walk backwards into the future.","Now is a good time to buy stock.","Now is the time to go ahead and pursue that love interest!","Now is the time to try something new","Now is the time to try something new.","Others can help you now.","Pennies from heaven find their way to your doorstep this year!","People are attracted by your Delicate[sic] features.","People find it difficult to resist your persuasive manner.","Perhaps you've been focusing too much on saving.","Physical activity will dramatically improve your outlook today.","Place special emphasis on old friendship.","Please visit us at www.wontonfood.com","Practice makes perfect.","Protective measures will prevent costly disasters.","Put your mind into planning today. Look into the future.","Remember to share good fortune as well as bad with your friends.","Rest has a peaceful effect on your physical and emotional health.","Resting well is as important as working hard.","Romance moves you in a new direction.","Savor your freedom -- it is precious.","Say hello to others. You will have a happier day.","Self-knowledge is a life long process.","Sloth makes all things difficult; industry all easy.","Small confidences mark the onset of a friendship.","Society prepares the crime; the criminal commits it.","Someone you care about seeks reconciliation.","Soon life will become more interesting.","Stand tall. Don't look down upon yourself. ","Stop searching forever, happiness is just next to you.","Success is a journey, not a destination.","Take care and sensitivity you show towards others will return to you.","Take the high road.","The austerity you see around you covers the richness of life like a veil.","The best prediction of future is the past.","The change you started already have far-reaching effects. Be ready.","The change you started already have far-reaching effects. Be ready.","The first man gets the oyster, the second man gets the shell.","The harder you work, the luckier you get.","The night life is for you.","The one that recognizes the illusion does not act as if it is real.","The only people who never fail are those who never try.","The person who will not stand for something will fall for anything.","The philosophy of one century is the common sense of the next.","The saints are the sinners who keep on trying.","The secret to good friends is no secret to you. ","The small courtesies sweeten life, the greater ennoble it.","The smart thing to do is to begin trusting your intuitions.","The strong person understands how to withstand substantial loss.","The sure way to predict the future is to invent it.","The truly generous share, even with the undeserving.","The value lies not within any particular thing, but in the desire placed on that thing.","The weather is wonderful.","There is no mistake so great as that of being always right.","There is no wisdom greater than kindness. ","There is not greater pleasure than seeing your lived (sic) ones prosper.","There's no such thing as an ordinary cat.","Those who care will make the effort.","Time and patience are called for many surprises await you!. (sic)","Time is precious, but truth is more precious than time","To know oneself, one should assert oneself.","Today is the conserve yourself, as things just won't budge.","Today, your mouth might be moving but no one is listening.","Tonight you will be blinded by passion.","Use your eloquence where it will do the most good.","Welcome change.","Well done is better than well said.","What's hidden in an empty box?","What's yours in mine, and what's mine is mine.","When your heart is pure, your mind is clear.","Wish you happiness.","You always bring others happiness.","You are a person of another time.","You are a talented storyteller. ","You are admired by everyone for your talent and ability.","You are almost there.","You are busy, but you are happy.","You are generous to an extreme and always think of the other fellow.","You are going to have some new clothes.","You are in good hands this evening.","You are never selfish with your advice or your help.","You are next in line for promotion in your firm.","You are offered the dream of a lifetime. Say yes!","You are open-minded and quick to make new friends. ","You are solid and dependable.","You are soon going to change your present line of work.","You are talented in many ways.","You are the master of every situation. ","You are very expressive and positive in words, act and feeling.","You are working hard.","You begin to appreciate how important it is to share your personal beliefs.","You desire recognition and you will find it.","You have a deep interest in all that is artistic.","You have a friendly heart and are well admired. ","You have a shrewd knack for spotting insincerity.","You have a yearning for perfection. (3)","You have an active mind and a keen imagination.","You have an ambitious nature and may make a name for yourself.","You have exceeded what was expected.","You have the power to write your own fortune.","You have yearning for perfection.","You look pretty.","You love challenge.","You love chinese food.","You make people realize that there exist other beauties in the world.","You never hesitate to tackle the most difficult problems. ","You seek to shield those you love and like the role of provider. ","You should be able to make money and hold on to it.","You should be able to undertake and complete anything.","You understand how to have fun with others and to enjoy your solitude.","You will always be surrounded by true friends.","You will always get what you want through your charm and personality.","You will always have good luck in your personal affairs.","You will be a great success both in the business world and society. ","You will be blessed with longevity.","You will be successful in your work.","You will be traveling and coming into a fortune.","You will be unusually successful in business.","You will become a great philanthropist in your later years.","You will become more and more wealthy.","You will enjoy good health.","You will enjoy good health; you will be surrounded by luxury.","You will find great contentment in the daily, routine activities.","You will have a fine capacity for the enjoyment of life.","You will have gold pieces by the bushel.","You will inherit a large sum of money.","You will make change for the better.","You will soon be surrounded by good friends and laughter.","You will take a chance in something in near future.","You will travel far and wide,both pleasure and business.","Your abilities are unparalleled.","Your ability is appreciated.","Your ability to juggle many tasks will take you far.","Your biggest virtue is your modesty.","Your character can be described as natural and unrestrained.","Your difficulties will strengthen you.","Your dreams are never silly; depend on them to guide you.","Your dreams are worth your best efforts to achieve them.","Your energy returns and you get things done.","Your first love has never forgotten you.","Your happiness is before you, not behind you! Cherish it.","Your hard work will payoff today.","Your heart will always make itself known through your words.","Your home is the center of great love.","Your ideals are well within your reach.","Your infinite capacity for patience will be rewarded sooner or later.","Your leadership qualities will be tested and proven.","Your life will be happy and peaceful.","Your life will get more and more exciting.","Your love life will be happy and harmonious.","Your love of music will be an important part of your life.","Your loyalty is a virtue, but not when it's wedded with blind subbornness.","Your mind is your greatest asset.","Your quick wits will get you out of a tough situation.","Your success will astonish everyone. ","Your talents will be recognized and suitably rewarded.","Your work interests can capture the highest status or prestige."};
	static String[] slap = {"reflectie","a frying pan","a gigantic fish","common sense","chicago","a desktop","a couch","a nice cow pattie","chaos","missing no","a decrepid myspace account","it's flux vortex cannon","a rhino","java.reflect.*","billy mays","OpenOffice.org","freshly salted nuts","decomposing fish","a fat sweaty man after he's eaten a salami sandwich"};
	static String[] colors = {"\u000302","\u000303","\u000305","\u000306","\u000308","\u000309","\u000311","\u000312","\u000313"};
	static String[] medical = {"Aarskog syndrome","Achondroplasia","Achromatopsia","Acoustic neuroma","Adrenal hyperplasia","Adrenoleukodystrophy","Agenesis of corpus callosum","Aicardi syndrome","Alagille syndrome","Albinism","Alopecia areata","Alstrom syndrome","Alpha-1-antitrypsin deficiency","Ambiguous genitalia","Androgen insensitivity syndrome","Anorchia","Angelman syndrome","Anopthalmia","Apert syndrome","Arthrogryposis","Ataxia","Autism / Asperger syndrome","Bardet-Biedl syndrome","Basal cell carcinoma","Batten disease","Beckwith-Wiedemann syndrome","Blepharophimosis","Branchio-Oto-Renal syndrome","xeroderma pigmentosa","Cardiofaciocutaneous syndrome","Celiac sprue","Charcot-Marie-Tooth","CHARGE association","Chromosome anomalies","Cleft lip and/or cleft palate","Cockayne syndrome","Coffin-Lowry syndrome","Coffin-Siris syndrome","Congenital heart defects","Connective tissue conditions","Marfan syndrome","pseudoxanthoma elasticum","Cutis laxa","skeletal dysplasia","Ehlers Danlos syndrome","hyperextensible joints","Beals syndrome","congenital contractural arachnodactyly","thalassemia major anemia","beta-thalassemia anemia","Conjoined twins","Cornelia de Lange syndrome","Costello syndrome","hemifacial microsomia","neurofibromatosis","Cri-du-Chat","Cystic fibrosis","Cystinosis","Cystinuria","Dandy-Walker syndrome","Diabetes","DiGeorge syndrome","Down syndrome","Dubowitz syndrome","achondroplasia","multiple exostoses","Dysautonomia","Dystonia","Ectodermal dysplasia","Ehlers Danlos syndrome","Epidermolysis bullosa","Thrombophilia","Fanconi anemia","Fetal alcohol syndrome","FG syndrome","Fragile-X syndrome","Friedreich ataxia","Freeman Sheldon syndrome","craniocarpotarsal dystrophy","Galactosemia","Gardner syndrome","intestinal polyposis","Gaucher disease","Glycogen storage disease","Goldenhar syndrome","hemifacial microsomia","Gorlin syndrome","basal cell carcinoma","Hallermann Streiff syndrome","Hearing problems","Hemochromatosis","Hemophilia","Hemoglobinopathies","Hereditary hemorrhagic telangiectasia","Osler-Weber-Rendu syndrome","Hereditary spastic paraplegia","familial spastic paraparesis","Hermansky-Pudlak syndrome","Hirschsprung anomaly","Holoprosencephaly","Huntington disease","hydranencephaly","Ichthyosis","epidermolytic hyperkeratosis","multiple sulfatase deficiency","keratoderma","Refsum disease","Incontinentia pigmenti","Infertility","Intestinal problems","Joseph disease","Joubert syndrome","Kabuki syndrome","Kidney conditions","Klinefelter syndrome","Klippel-Feil syndrome","Klippel-Trenaunay syndrome","Langer-Giedion syndrome","Laurence-Moon-Biedl syndrome","Leber Optic Atrophy","Leigh disease","Lesch-Nyhan syndrome","Leukodystrophy","Adrenoleukodystrophy","Cerebral Autosomal Dominant Arteriopathy with Subcortical Infarcts","Leukoencephalopathy","Cerebrotendinous Xanthomatosis","Metachromatic Leukodystrophy","Ovarioleukodystrophy","Pelizaeus-Merzbacher Disease","van der Knaap syndrome","Zellweger syndrome","Lissencephaly","Subcortical Band Heterotopia","Miller-Dieker syndrome","Microlissencephaly","Norman-Roberts syndrome","Polymicrogyria","Schizencephaly","Muscle-Eye-Brain Disease","Walker-Warburg syndrome","biliary atresia","Alagille syndrome","tyrosinemia","neonatal hepatitis","Wilson disease","Lowe syndrome","Lymphedema","Maffucci syndrome","multiple cartilaginous enchondromatosis","Malignant hyperthermia","Maple syrup urine disease","Marinesco-Sjogren Syndrome","Marfan syndrome","Menkes syndrome","biotinidase deficiency","carbohydrate deficient glycoprotein syndrome","Crigler-Najjar syndrome","diabetes insipidus","galactosemia","glucose-6-phosphate dehydrogenase","fatty acid oxidation disorders","glutaric aciduria","hypophosphatemia","lactic acidosis","lysosomal storage diseases","mannosidosis","maple syrup urine","pyruvate dehydrogenase deficiency","Moebius syndrome","Mucolipidosis","Mucopolysaccharidosis","Hunter syndrome","Hurler syndrome","Maroteaux-Lamy syndrome","Sanfilippo syndrome","Scheie syndrome","Morquio syndrome","Multiple hereditary exostoses","Muscular dystrophy","Duchenne","spinal muscular atrophy","Myotonic dystrophy","Nager & Miller syndromes","Nail Patella syndrome","Narcolepsy","Huntington disease","Neurofibromatosis","von Recklinghausen","Neuromuscular conditions","Niemann-Pick disease","Noonan syndrome","Organic acidemias","Osler-Weber-Rendu syndrome","Osteogenesis imperfecta","Oxalosis & hyperoxaluria","Pallister-Hall syndrome","Pallister-Killian syndrome","Teschler-Nicola syndrome","Periodic paralysis","Phenylketonuria","Polycystic kidney disease","Popliteal pterygium syndrome","Porphyria","Prader-Willi syndrome","Progeria","Proteus syndrome","Prune belly syndrome","Pseudoxanthoma elasticum","Psychiatric conditions","Refsum disease","Retinal degeneration","Retinitis pigmentosa","Usher syndrome","Retinoblastoma","Rett syndrome","Robinow syndrome","Rubinstein-Taybi syndrome","Russell-Silver syndrome","Schizencephaly","Shwachman syndrome","Sickle cell anemia","Skeletal dysplasia","Smith-Lemli-Opitz syndrome","Smith-Magenis syndrome","Sotos syndrome","Spina bifida","Spinal muscular atrophy","Marshall syndrome","Sturge-Weber","Tay-Sachs disease","Thalassemia","Thrombocytopenia absent radius syndrome","Tourette syndrome","Treacher Collins syndrome","Tuberous sclerosis","Turner syndrome","Velo-cardio-facial syndrome","Von Hippel-Lindau syndrome","Waardenburg syndrome","Weaver syndrome","Werner syndrome","Williams syndrome","Wilson disease","Xeroderma pigmentosum","Zellweger syndrome"};
	public static String p(String cmd)
	{
		JEP j = new JEP();
		j.addStandardConstants();
		j.addStandardFunctions();
		j.addComplex();
		j.parseExpression(cmd);
		j.addFunction("fact",new Factorial());
							
		if (j.hasError()) {
			return j.getErrorInfo().toString();
		} else {
			return j.getValueAsObject().toString();
		}
	}
	public static String version(String cmd)
	{
		return version;
	}
	public static String help(String cmd)
	{
		return "!goons !q !reverse !reflect !color !random !dict !flip !eightball !fortune !slap !version";		
	}
	public static String slap(String cmd)
	{
		return "\u0001ACTION slaps "+cmd+(cmd.endsWith(" ")?"":" ")+"with "+slap[(int)(Math.random()*slap.length)]+"\u0001";
		
	}
	public static String diagnosis(String cmd)
	{
		return "you have: "+medical[(int)(Math.random()*medical.length)];
		
	}
	public static String aggies(String cmd)
	{
		return "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
	}
	// public static String insult(String cmd)
	// {
		// String nick=cmd.trim();
		// return insults[(int)(Math.random()*insults.length)].replaceAll("\\^",nick).replaceAll("\\$","he").replaceAll("\\%","his").replaceAll("\\#","him");
	// }
	// public static String insultf(String cmd)
	// {
		// String nick=cmd.trim();
		// return insults[(int)(Math.random()*insults.length)].replaceAll("\\^",nick).replaceAll("\\$","she").replaceAll("\\%","her").replaceAll("\\#","her");
	// }
	public static String temp(String cmd)
	{
		return String.valueOf(slap.length);
	}
	public static String suckcock(String cmd)
	{
		return "\u0001ACTION sucks "+(cmd.equals("")?"your ":(cmd.endsWith(" ")?cmd.substring(0,cmd.length()-1):cmd)+"'s ") +"cock. Yum!\u0001";
		
	}
	public static String fortune(String cmd)
	{
		return fortunes[(int)(Math.random()*fortunes.length)];
	}
	public static String urinal(String cmd)
	{
		int number=Integer.parseInt(cmd);
		if (number>100000)
			return "too big, do less than 100000";
		
		int[] urinal = new int[number];
                urinal[0] = 1;
                urinal[urinal.length-1] = 1;
               
                for (int a=0; a<urinal.length; a++) {
                        //check vacancies
                        int count = 0, vacantSize = 0, vacantIndex = 0;
                        for (int i=1; i<urinal.length; i++) {
                                if (urinal[i]==0) {
                                        count++;
                                }
                                else {
                                        if (count >= vacantSize) {
                                                vacantSize = count;
                                                vacantIndex = i-count;
                                        }
                                        count = 0;
                                }
                        }
                        //take best urinal
                        int vacant = vacantIndex+vacantSize/2;
                        if (urinal[vacant-1]==0 && urinal[vacant+1]==0) {
                                urinal[vacant]=1;
                        }
                        vacantSize = 0;
                        vacantIndex = 0;
                }
				String result="";
                int count = 0;
                for (int i=0; i<urinal.length; i++) {
					result+=urinal[i];
						if (urinal[i] == 1) {
                                count++;
                        }
                }
              //out: urinals, efficiency
				
				if (number<100)
					return result +" " + 100*count/(double)number+"%";
				else
					return 100*count/(double)number+"%";
		
	}
	public static String dict(String cmd)
	{
		cmd=cmd.substring(0,(cmd.contains(" ")?cmd.indexOf(" "):cmd.length()));//cant have spaces
		try{		
		Socket sock=new Socket();
		sock.connect(new InetSocketAddress("dict.org",2628));	
		String data="DEFINE wn "+cmd+"\r\nQUIT\r\n";
		sock.getOutputStream().write(data.getBytes());
		BufferedReader in= new BufferedReader(new InputStreamReader(sock.getInputStream()));


		in.readLine();
		String line = in.readLine();
		if(line.startsWith("552"))
			return "No definitions for "+cmd;
		//well it has to be 150ish anyways
		
		int defCount=Integer.parseInt(line.substring(4,line.indexOf(" ",4)));

		in.readLine();//151 reply

		in.readLine();//repeat the word again
		boolean done=false;
		line=in.readLine();
		int spacing=line.indexOf(":")+2;
		String result=line.substring(spacing);//5 spaces GONE
		while(!done)
		{
			line=in.readLine();
			//System.out.println(line);
			if(line.startsWith("        "))//8 spaces minimum for a tab(continuation of first definition)
				result+=line.substring(spacing-1); //keep one of the spaces, it's supposed to be 10
			else
				done=true;
		}
		return result;
		}//end try
		catch(Exception e)
		{
			e.printStackTrace();
			return "IOException :<";
			
		}
	}
	
	
	
	public static String eightball(String cmd)
	{
		switch((int)(Math.random()*8))
		{
			case 0: return "Try again later";
			case 1: return "Definately";
			case 2: return "Not a chance";
			case 3: return "I'm feeling good vibes";
			case 4: return "Try asking YOURSELF that question";
			case 5: return "Yes";
			case 6: return "Maybe";
			case 7:	return "Ask Thyth, he answered this question back in '96";
			default: return "";
		}
		
	}
	public static String flip(String cmd)
	{
		switch((int)(Math.random()*2))
		{
			case 0: return "heads";
			case 1: return "tails";
			default: return "";
		}
	}
	public static String goons(String cmd)
	{
		return q("67.222.138.111");
	}
	public static String qplayers(String cmd)
	{
		Server goons=new Server(cmd, 28000);
		try{
			goons.query();
			return goons.get(Server.Field.PLAYERS);
		}catch(IOException e)
		{
			return "Timeout :<";
		}
	}
	public static String q(String cmd)
	{
		Server svr=new Server(cmd, 28000);
		try{
			svr.query();
			return svr.get(Server.Field.MISSION) + " [" + svr.get(Server.Field.PLAYER_COUNT) + "/" + svr.get(Server.Field.PLAYER_MAX)+"]";
		}catch(IOException e)
		{
			return "Timeout :<";
		}
	}
	
	
	//an incomplete function to place cyrillic combining characters 
	//after each letter of input in a random fashion
	//this should generate lots of garbage because the output
	//theoretically should be outside of the line it was typed on
	
	//everything should be working fine, except the unicode array below is incorrect. 
	//find the real characters
	static char zalgoDown[]={'\u033A','\u032F','\u0349','\u0348','\u0331','\u034D','\u0329','\u034E','\u033C','\u0316','\u031F','\u0318','\u0320','\u0354'};
	static char zalgoUp[]={'\u0367','\u0357','\u0304','\u030E','\u0313','\u030A','\u0342','\u0365','\u0307','\u0346','\u0305','\u036E','\u0351','\u030D','\u030F'};
	public static String zalgo(String str)
	{
		String out="";
		for(int i=0;i<str.length();i++)
		{
			out+=str.charAt(i);
			for(int j=0;j<4;j++)
				out+=zalgoDown[(int)(Math.random()*zalgoDown.length)];
			for(int j=0;j<4;j++)
				out+=zalgoUp[(int)(Math.random()*zalgoUp.length)];
		}
		return out;
	}
	
	public static String random(String str)
	{
		ArrayList<String> l = new ArrayList<String>();
		Scanner sc = new Scanner(str);
		while (sc.hasNext())
			l.add(sc.next());
		return l.get((int)(Math.random()*l.size()));
	}
	

	public static String color(String str)
	{	
		String out="";
		for(int i=0;i<str.length();i++)
		{
			int index = (int)(Math.random()*colors.length);
				out+=colors[index]+str.charAt(i);
		}
		return out;
		
	}
	
	/*reverses all the characters with consideration of color codes 
	 *staying in the same place and in the right order (05 is not 50)
	 */
	public static String reverse(String in)
	{
		// \u0003 is color code (followed by two numbers)
		// \u0001 is action or ctcp
		// \u0002 is bold code
		// \u001F is underline code
		// \u0016 is italic code
		// \u000F is normal
		String lastColor="";
		int underCount=0;
		int boldCount=0;
		int italicCount=0;
		
		
		String out="";
		for(int i=0;i<in.length();i++)
		{
			if(in.charAt(i)=='\u0003') //color
			{
				if(lastColor.equals(""))
					out="\u000301"+out;
				else
					out=lastColor+out;
				lastColor=in.substring(i,i+3);
				i++;i++;
			}
			//else if(in.charAt(i)=='\u0002')//bold
			else
				out=in.charAt(i)+out;
		}
		out=lastColor+out;//if lastColor has not been changed (because of a color) it's still "" and this does nothing
		return out;
	}
	public static String reflect(String cmd)
	{
		return cmd + reverse(cmd);
	}	
	
}