public class BitwiseHuman extends Main {
  
  public static final int UREA_CYCLE = Excretion;
  
  public static final int REDPIXEL = 0;
  public static final int BLUEPIXEL = 0;
  public static final int GREENPIXEL = 0;
  public static final int ALPHA = 0 ;
  public static final int WHITE = 0;
  public static final int UNDEFINED = U;
  
  private String nameHuman;
  private String descriptionHuman;
  private int NAGS;
  private int urea = UREA_CYCLE;
  
  public BitwiseHuman(String otherNameHuman, otherDescriptionHuman,  int otherUrea, int otherNAGS)
    nameHuman = otherNameHuman;
    descriptionHuman = otherDescriptionHuman;
    urea = otherUrea;
    setNAGS(otherNAGS);
}

   public BitwiseHuman(String otherNameHuman){
     this(otherNameHuman, "Undefined Description", UREA_CYCLE, UNDEFINED);
     
     public void setNameHuman(String h){nameHuman = h;}
     public String getNameHuman(){return nameHuman;}
     public void setDescriptionHuman(String h1){descriptionHuman = h1;}
     public String getDescriptionHuman(){return descriptionHuman;}
     public int getNAGS(){return NAGS;}
     public void setNAGS(int nags){
       if(nags == REDPIXEL || nags = BLUEPIXEL || nags = GREENPIXEL || nags = ALPHA || nags = WHITE){
         NAGS = nags;
       }else{
         NAGS = UNDEFINED;
       }
       public void setUrea(int k){urea = k;}
       public int getUrea(){return urea;}
     
