import java.lang.Math;
import java.util.Scanner;
import java.text.DecimalFormat;


public class MRADriver {

  public static void main(String[] args) {

      Alpha[] arrayOfJobs = new Alpha[100];
      /* load elements into this array */
      for (int i = 0; i < 100; i++){
          arrayOfJobs[i] = new Alpha();
          arrayOfJobs[i].setJobID(i/10 + 1);
          arrayOfJobs[i].setDataSize((i+arrayOfJobs[i].getJobID())%11);
          arrayOfJobs[i].setAlphaJD(arrayOfJobs[i].getDataSize() *
              (10 + ((arrayOfJobs[i].getJobID() - 1)  *5)));

      }
      MachineType[] arrayOfMachines = new MachineType[10];
      /* load elements into this array */
      for (int i = 0; i < 10; i++){
          arrayOfMachines[i] = new MachineType();
          arrayOfMachines[i].setTypeNo(i + 1);
          arrayOfMachines[i].setMemory(i + 1);
          // Change 0.25 to alter the price distribution
          // arrayOfMachines[i].setPrice(arrayOfMachines[i].getTypeNo() * 10);
          arrayOfMachines[i].setPrice(arrayOfMachines[i].getTypeNo() * 0.25);
      }

    /* go through the loop and print */
    System.out.println("Contents of the arrayOfJobs: ");
    System.out.println("Contents of the array: ");
    System.out.println("i"+ "\t"+"jobID" + "\t"
    + "Data" + "\t"
    + "AlphaJD" + "\t");

    for (int i = 0; i < arrayOfJobs.length; i++) {
      System.out.println(i + "\t" + arrayOfJobs[i].getJobID() + "\t"
      + arrayOfJobs[i].getDataSize() + "\t"
      + arrayOfJobs[i].getAlphaJD());

    }
    /* go through the loop and print */
    System.out.println();
    System.out.println("Contents of the arrayOfMachines: ");
    System.out.println("TypeNo" + "\t"
    + "Mem" + "\t"
    + "Price");

    for (int j = 0; j < arrayOfMachines.length; j++) {
      System.out.println(arrayOfMachines[j].getTypeNo() + "\t"
      + arrayOfMachines[j].getMemory() + "\t"
      + arrayOfMachines[j].getPrice());

    }
    /* Taking user input */
    double budget;
    Scanner input = new Scanner(System.in);
    System.out.println();
    System.out.println("Enter the budget: ");
    budget = input.nextDouble();
    // double budget = 100;

    DecimalFormat df = new DecimalFormat("#.##");


    /* go through the loop and print */
    System.out.println();
    System.out.println("Calculating... ");
    System.out.println("jobID" + "\t"
    + "Data" + "\t"
    + "AlphaJD" + "\t"
    + "TypeNo" + "\t"
    + "Mem" + "\t"
    + "Price" + "\t"
    + "TR" + "\t"
    + "X" + "\t"
    + "Cost");
    for (int j = 0; j < arrayOfMachines.length; j++) {
      for (int i = 0; i < arrayOfJobs.length; i++) {
        double timeRepresentation = 1;
        double numberOfMachines = 1;
        double ec = 1;
        timeRepresentation = (arrayOfJobs[i].getDataSize() / arrayOfMachines[j].getMemory()) * arrayOfJobs[i].getAlphaJD();
        numberOfMachines = budget / (timeRepresentation * arrayOfMachines[j].getPrice());
        ec = arrayOfMachines[j].getPrice() * Math.floor(numberOfMachines) * timeRepresentation;
      System.out.println(arrayOfJobs[i].getJobID() + "\t"
      + arrayOfJobs[i].getDataSize() + "\t"
      + arrayOfJobs[i].getAlphaJD() + "\t"
      + arrayOfMachines[j].getTypeNo() + "\t"
      + arrayOfMachines[j].getMemory() + "\t"
      + arrayOfMachines[j].getPrice() + "\t"
      + df.format(timeRepresentation) + "\t"
      // + Math.round(timeRepresentation) + "\t"
      + Math.round(Math.floor(numberOfMachines)) + "\t"
      + df.format(ec));
      }
    }
  }
}


class Alpha{
    private int jobID = 0;
    private double dataSize = 0;
    private double alphaJD = 0;
    /* getters and setters */
    public int getJobID(){
        return this.jobID;
    }
    public void setJobID(int jobID){
        this.jobID = jobID;
    }

    public double getDataSize(){
        return this.dataSize;
    }
    public void setDataSize(double dataSize){
        this.dataSize = dataSize;
    }

    public double getAlphaJD(){
        return this.alphaJD;
    }
    public void setAlphaJD(double alphaJD){
        this.alphaJD = alphaJD;
    }

}

class MachineType{
  private int typeNo = 0;
  private double memory = 0;
  private double price = 0;
  private int noOfInstances;

  /* getters and setters */
  public int getTypeNo(){
      return this.typeNo;
  }
  public void setTypeNo(int typeNo){
      this.typeNo = typeNo;
  }
  public double getMemory(){
      return this.memory;
  }
  public void setMemory(double memory){
      this.memory = memory;
  }
  public double getPrice(){
      return this.price;
  }
  public void setPrice(double price){
      this.price = price;
  }
}
