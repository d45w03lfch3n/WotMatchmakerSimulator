package de.DrP3pp3r.wot.mm_sim;

public class MatchmakerSimulation 
{
    public static void main( String[] args )
    {
        System.out.println( "MatchmakerSimulation was started!" );
        TankType is3 = new TankType("IS-3", TankClass.HEAVY_TANK);
        TankType loewe = new TankType("Löwe", TankClass.HEAVY_TANK);
        TankType progetto46 = new TankType("Progetto 46", TankClass.MEDIUM_TANK);
        
        TankTypeSelector selector = new TankTypeSelector();
        selector.addSelectionInfo(new TankTypeSelectionInfo(is3, 0.4));
        selector.addSelectionInfo(new TankTypeSelectionInfo(loewe, 0.75));
        selector.addSelectionInfo(new TankTypeSelectionInfo(progetto46, 1.0));
        
        TankType selectedTankType = selector.getTankType(0.5);
        
        System.out.format("The selected tank type is '%s.", selectedTankType.getName());
    }
}
