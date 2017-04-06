package org.serc.model.attackgraph;


public abstract class PrivilegeNode extends BaseNode {
    
    private boolean goal = false;
    
    public PrivilegeNode(String id, String info, String type, String initial) {
        super(id, info, type, initial);
    }

    @Override
    public Type getNodeType() {
        return Type.privilege;
    }

    public abstract String getPrivilegeType();

    public boolean isGoal() {
        return goal;
    }

    public void setGoal(boolean goal) {
        this.goal = goal;
    }

}
