import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
class cardholder extends JPanel implements ActionListener{
JPanel holder=new JPanel();
CardLayout c;
JPanel e=new JPanel();
snakepanel p;
JButton oneminute=new JButton("One minute challenge");
JButton start=new JButton("Configuration");
JButton fiveminute=new JButton("Five minute challenge");
JButton tenminute=new JButton("Ten minute challenge");
JButton wall=new JButton("Wall Challenge");
cardholder(){
setLayout(new BorderLayout());
c=new CardLayout();
holder.setLayout(c);
p=new snakepanel();
//p.setSize(200,200);
holder.add(e,"none");
holder.add(p,"snake");
start.addActionListener(this);
oneminute.addActionListener(this);
fiveminute.addActionListener(this);
tenminute.addActionListener(this);
//wall.addActionListener(this);
add(start,BorderLayout.NORTH);
add(oneminute,BorderLayout.SOUTH);
add(fiveminute,BorderLayout.EAST);
add(tenminute,BorderLayout.WEST);
//add(wall);
add(holder,BorderLayout.CENTER);
c.show(holder,"none");
}
public void actionPerformed(ActionEvent ae){
if(ae.getActionCommand()=="Configuration"){
configframe f=new configframe("Game Controls and Instructions");
f.setSize(500,200);
f.setLocation(300,200);
f.setVisible(true);
System.out.println("Command Successful");
}
if(ae.getActionCommand()=="One minute challenge"){
c.show(holder,"snake");
System.out.println("Command Successful");
p.time=60;
start.setEnabled(false);
//wall.setEnabled(false);
oneminute.setEnabled(false);
fiveminute.setEnabled(false);
tenminute.setEnabled(false);
remove(start);
//remove(wall);
remove(oneminute);
remove(fiveminute);
remove(tenminute);
}
if(ae.getActionCommand()=="Five minute challenge"){
c.show(holder,"snake");
System.out.println("Command Successful");
p.time=300;
start.setEnabled(false);
oneminute.setEnabled(false);
fiveminute.setEnabled(false);
tenminute.setEnabled(false);
remove(start);
remove(oneminute);
remove(fiveminute);
remove(tenminute);
}
if(ae.getActionCommand()=="Ten minute challenge"){
c.show(holder,"snake");
System.out.println("Ten minute challenge");
p.time=600;
start.setEnabled(false);
//wall.setEnabled(false);
oneminute.setEnabled(false);
fiveminute.setEnabled(false);
tenminute.setEnabled(false);
remove(start);
//remove(wall);
remove(oneminute);
remove(fiveminute);
remove(tenminute);
}
}
}
class snakeframe extends JFrame{
cardholder c;
snakeframe(String str){
super(str);
Container cpane=getContentPane();
c=new cardholder();
cpane.add(c);
addWindowListener(new WindowAdapter(){
public void windowClosing(WindowEvent we){
System.exit(0);
}
});
}
public static void main(String args[]){
snakeframe f=new snakeframe("Java Snake");
f.setSize(500,500);
f.setVisible(true);
}
}
class snakepanel extends JPanel implements Runnable,KeyListener,ActionListener{
private static volatile int endcons=0;
private static volatile boolean ispaused=false;
private static volatile boolean lockpanel=false;
private static volatile boolean lockpanel2=false;
private static volatile int flagpause=0;
private static volatile boolean endgame=false;
private static volatile boolean running=false;
private static volatile int dirflag=0;
private static volatile int headx=250;
private static volatile int heady=500;
private static volatile int cookiex;
private static volatile int cookiey;
private static volatile int width=500;
private static volatile int height=500;
private static volatile int cellx[];
private static volatile int celly[];
private static volatile int growflag;
private static volatile int somex,somey;
private static final int maxlen=1000;
public static volatile int time=0;
private static volatile int comptime=0;
public static int score=0; 
private static volatile int buttonlock;
private static volatile long startime,endtime,sleeptime;
Graphics g,gd;
Image img,nouse;
Thread t;
private static int flag=0;
private int kcode;
snakepanel(){
cellx=new int[1000];
celly=new int[1000];
growflag=0;
setLayout(new FlowLayout());
t=new Thread(this);
Timer t=new Timer(5000,this);
t.start();
//addMouseListener(this);
addKeyListener(this);
setFocusable(true);
requestFocus();
//setBackground(Color.yellow);
setVisible(true);
}
//
//
//
public void actionPerformed(ActionEvent ae){
cookiex=(int)(Math.random()*width-10);
cookiey=(int)(Math.random()*height-10);
if(!ispaused&&time!=0){
comptime+=5;
}
}
//
//
//
public void addNotify(){
super.addNotify();
}
//
//
//
public void run(){
//code for running animation
System.out.println("run() is working");
running=true;
while(running){
startime=System.nanoTime();
render();
update();
sleep();
endtime=System.nanoTime();
sleeptime=(endtime-startime)/1000000L;
//System.out.println("Time taken is: "+sleeptime);
//System.out.println("Inside methods");
}
}
//
//
//
public void keyTyped(KeyEvent ke){
}
//
//
//
public void keyPressed(KeyEvent ke){
if(lockpanel==false){
kcode=ke.getKeyCode();
if(kcode==ke.VK_ENTER)
if(flag==0){
{
t.start();
flag=1;
paintscreen("game started");}
}
if(!lockpanel2){
if(kcode==ke.VK_UP){
if(buttonlock!=3&&dirflag!=4){
//code to move up
//System.out.println("Up received");
dirflag=3;
buttonlock=3;
//System.out.println("dirflag:"+dirflag);
update();
}
}
if(kcode==ke.VK_DOWN){
if(buttonlock!=4&&dirflag!=3){
//code to move down
//System.out.println("Down received");
dirflag=4;
buttonlock=4;
//System.out.println("dirflag:"+dirflag);
update();
}
}
if(kcode==ke.VK_LEFT){
if(buttonlock!=1&&dirflag!=2){
//code to move left
//System.out.println("Left received");
dirflag=1;
buttonlock=1;
//System.out.println("dirflag:"+dirflag);
update();
}
}
if(kcode==ke.VK_RIGHT){
if(buttonlock!=2&&dirflag!=1){
//code to move right
//System.out.println("Right received");
dirflag=2;
buttonlock=2;
//System.out.println("dirflag:"+dirflag);
update();
}
}
}
if(kcode==ke.VK_ESCAPE){
System.out.println("Escaped Received");
if(endcons==1){
endcons=0;
}
switch(flagpause){
case 0:
{
//ispaused=true;
//paintscreen("game paused");
flagpause=1;
stopgame();
break;
}
case 1:
{
//ispaused=false;
//paintscreen("game resumed");
flagpause=0;
resumegame();
break;
}
}
}
if(kcode==ke.VK_0){
endcons+=1;
ispaused=true;
paintscreen("Are you sure?.Press zero to end game.To resume press escape key");
if(endcons==2){
ispaused=true;
endgame=true;
System.out.println("Game Ended");
paintscreen("Game Ended.Your Score is: "+score);
lockpanel=true;
}
}
}
}
//
//
//
public void render(){}
//
//
//
public void resumegame(){
System.out.println("resumegame called");
ispaused=false;
lockpanel2=false;
}
//
//
//
public void stopgame(){
System.out.println("stopgame called");
ispaused=true;
lockpanel2=true;
}
//
//
//
public void update(){
if(!ispaused){
if(dirflag==1){
if(headx==0||headx>0){
if(headx==0){
headx=500;}
paintsnake(headx,heady);
headx-=5;
}
}
if(dirflag==2){
if(headx<500||headx==500){
if(headx==500){
headx=0;}
paintsnake(headx,heady);
headx+=5;
}
}
if(dirflag==3){
if(heady==0||heady>0){
if(heady==0){
heady=500;}
paintsnake(headx,heady);
heady-=5;
}
}
if(dirflag==4){
if(heady<500||heady==500){
if(heady==500){
heady=0;}
paintsnake(headx,heady);
heady+=5;
}
}
}
}
//
//
//
public void paintsnake(int x,int y){
//System.out.println("Inside paintsnake");
try{
t.sleep(1);}catch(InterruptedException e){}
/*for(int i=1;i<=growflag;i++){
if(cellx[i]<headx&&headx<cellx[i+1]+5){
if(celly[i]<heady&&heady<celly[i+1]+5){
ispaused=true;
System.out.println("Game Over");
System.out.println("Your Score is: "+score);
}
}
}*/
img=createImage(width,height);
gd=img.getGraphics();
gd.setColor(Color.yellow);
if(cookiex-5<headx&&headx<cookiex+15){
if(cookiey-5<heady&&heady<cookiey+15){
score+=20;
growflag+=1;
cookiex=(int)(Math.random()*width);
cookiey=(int)(Math.random()*height);
cellx[growflag]=headx;
celly[growflag]=heady;
//paintsnake(headx,heady);
}
}
gd.fillRect(0,0,width,height);
gd.setColor(Color.black);
gd.drawString("Your current score is: "+score,50,20);
gd.drawString("Time Left: "+(time-comptime)+" secs",250,20);
gd.setColor(Color.black);
gd.fillRect(headx,heady,5,5);
gd.setColor(Color.blue);
for(int i=1;i<=growflag;i++){
gd.fillRect(cellx[i],celly[i],5,5);
}
for(int i=growflag+1;i>0;i--){
cellx[i]=cellx[i-1];
celly[i]=celly[i-1];
}
cellx[0]=headx;
celly[0]=heady;
gd.setColor(Color.red);
gd.fillOval(cookiex,cookiey,10,10);
gd.setColor(Color.blue);
g=this.getGraphics();
g.drawImage(img,0,0,null);
if(comptime==time){
System.out.println("your score is: "+score);
ispaused=true;
paintscreen("Game Over,Your Score is: "+score);
lockpanel=true;
time=0;
}
}
//
//
//
public void sleep(){
try{
t.sleep(0);
}catch(InterruptedException e){
System.out.println("Your game encountered an error");
}
}
//
//
//
public void keyReleased(KeyEvent ke){}
//
//
//
public void paintscreen(String str){
g=this.getGraphics();
img=createImage(width,height);
gd=img.getGraphics();
gd.setColor(Color.yellow);
gd.fillRect(0,0,500,500);
gd.setColor(Color.black);
gd.drawString(str,50,250);
g.drawImage(img,0,0,null);
} 
}
//
//
//
class configframe extends JFrame{
JLabel nav,start,controls,designed;
//ImageIcon rajat=new ImageIcon("rajat.jpg");
configframe(String str){
super(str);
Container cpane=getContentPane();
cpane.setLayout(new FlowLayout());
nav=new JLabel("For Navigating Snake-Direction Keys");
start=new JLabel("For Playing-Press any mission,then press 'enter' key,then press 'down' key");
controls=new JLabel("For Pausing/Resuming-Escape Key,For Exiting Game-Zero Key");
designed=new JLabel("Designed By-Rajat Saxena,CS,3rd Year,SKIT"); 
cpane.add(nav);
cpane.add(start);
cpane.add(controls);
cpane.add(designed);
}
}