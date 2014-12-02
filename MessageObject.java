import java.io.Serializable;


public class MessageObject extends DataObject  implements Serializable {

 public MessageObject(int startX, int startY, int endX, int endY) {
        this.sX = startX;
        this.sY = startY;
        this.eX = endX;
        this.eY = endY;
    }	
    public String getStrUserName() {
		return strUserName;
	}
	public void setStrUserName(String strUserName) {
		this.strUserName = strUserName;
	}
	private String strMessage;
	private String strUserName;
        int sX,sY,eX,eY;
        boolean bDraw;

    public int getsX() {
        return sX;
    }

    public void setsX(int sX) {
        this.sX = sX;
    }

    public int getsY() {
        return sY;
    }

    public void setsY(int sY) {
        this.sY = sY;
    }

    public int geteX() {
        return eX;
    }

    public void seteX(int eX) {
        this.eX = eX;
    }

    public int geteY() {
        return eY;
    }

    public void seteY(int eY) {
        this.eY = eY;
    }

    public boolean isbDraw() {
        return bDraw;
    }

    public void setbDraw(boolean bDraw) {
        this.bDraw = bDraw;
    }
	public MessageObject(){}
	public MessageObject(String str){
		setMessage(str);
	}
	public void setMessage(String str){
		this.strMessage = str;
	}
	public String getMessage(){
		return strMessage;
	}
	@Override
	public String toString() {
		return "MessageObject [strMessage=" + strMessage + ", strUserName="
				+ strUserName + "]"+ ", bDraw="
				+ bDraw + "]";
	}

}
