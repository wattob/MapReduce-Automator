import java.lang.Math;
import java.util.Scanner;
import java.text.DecimalFormat;
import java.util.Random;



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
      double rangeMax = 2.5;
      double rangeMin = 0.01;

      /* load elements into this array */
      for (int i = 0; i < 10; i++){

          arrayOfMachines[i] = new MachineType();
          arrayOfMachines[i].setTypeNo(i + 1);
          arrayOfMachines[i].setMemory(i + 1);
          // Change 0.25 to alter the price distribution
          // arrayOfMachines[i].setPrice(arrayOfMachines[i].getTypeNo() * 1);
          arrayOfMachines[i].setPrice(arrayOfMachines[i].getTypeNo() * 0.25);
          // Random rand = new Random();
          // double randomValue = rangeMin + (rangeMax - rangeMin) * rand.nextDouble();
          // arrayOfMachines[i].setPrice(arrayOfMachines[i].getTypeNo() * randomValue);

      }

    double budget;
    int jobID;
    double dataSize;
    double et = 0;


    Scanner input = new Scanner(System.in);
    System.out.println();
    System.out.println("Enter the budget: ");
    budget = input.nextDouble();

    System.out.println();
    System.out.println("Enter the Job ID Number (1-10): ");
    jobID = input.nextInt();
    jobID = (jobID - 1) * 10;

    System.out.println();
    System.out.println("Enter the dataSize (1-10): ");
    dataSize = input.nextDouble();
    dataSize = (dataSize % 11);
    double alphaJD;
    alphaJD = arrayOfJobs[jobID].getAlphaJD() * dataSize;


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
    + "Cost" + "\t"
    + "ET");

    for (int j = 0; j < arrayOfMachines.length; j++) {
        double timeRepresentation;
        double numberOfMachines;
        double cost;
        timeRepresentation = (dataSize / arrayOfMachines[j].getMemory()) * alphaJD;
        numberOfMachines = budget / (timeRepresentation * arrayOfMachines[j].getPrice());

        et = timeRepresentation / Math.floor(numberOfMachines);

        cost = arrayOfMachines[j].getPrice() * Math.floor(numberOfMachines) * timeRepresentation;
      System.out.println(arrayOfJobs[jobID].getJobID() + "\t"
      + dataSize + "\t"
      + alphaJD + "\t"
      + arrayOfMachines[j].getTypeNo() + "\t"
      + arrayOfMachines[j].getMemory() + "\t"
      + df.format(arrayOfMachines[j].getPrice()) + "\t"
      + df.format(timeRepresentation) + "\t"
      + Math.round(Math.floor(numberOfMachines)) + "\t"
      + df.format(cost) + "\t"
      + df.format(et));
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
