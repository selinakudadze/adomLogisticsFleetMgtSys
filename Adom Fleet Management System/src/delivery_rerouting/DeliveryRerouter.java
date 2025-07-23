package delivery_rerouting;
import models.Order;
import models.Vehicle;
public class DeliveryRerouter {
    public DeliveryRerouter(){}

    public void rerouteDeliveries(Order[] orders, Vehicle[] availVehicles){
        for(Order order: orders){
            if(needsRerouting(order)) {
                availVehicles = findReroute(order, availVehicles);
            }
        }
    }

    public Vehicle[] findReroute(Order order, Vehicle[] availVehicles){
        Vehicle bestCandidate = null;
        double bestCandidateDist = Float.MAX_VALUE;
        int indexBest = 0;
        int count = 0;
        for(Vehicle v: availVehicles){
            if(v == null){
                continue;
            }
            double currDist = getDist(order.getCurrentLongitude(), order.getCurrentLatitude(), v.getCurrentLong(), v.getCurrentLat());
            if(currDist < bestCandidateDist) {
                bestCandidateDist = currDist;
                bestCandidate = v;
                indexBest = count;
            }
            count += 1;
        }

        if (bestCandidate == null) {
            System.out.println("No available vehicle to reroute order " + order.getOrderId());
            return availVehicles;
        }

        order.setAssignedDriver(bestCandidate.getCurrentDriver());
        order.setDeliveryStatus("REROUTED");
        availVehicles[indexBest] = null;
        return availVehicles;
    }

    public boolean needsRerouting(Order order){
        boolean isStuck = "STUCK".equals(order.getDeliveryStatus());
        boolean waitingTooLong = order.getWaitTime().toMinutes() > 60;
        return isStuck || waitingTooLong;
    }

    private double getDist(double loc1long, double loc1lat, double loc2long, double loc2lat) {
        double ydiff = loc1long - loc2long;
        double xdiff = loc1lat - loc2lat;
        double dist = Math.sqrt((xdiff * xdiff) + (ydiff * ydiff));
        return dist;
    }
}