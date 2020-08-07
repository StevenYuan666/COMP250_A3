import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException; 


public class CatTree implements Iterable<CatInfo>{
    public CatNode root;
    
    public CatTree(CatInfo c) {
        this.root = new CatNode(c);
    }
    
    private CatTree(CatNode c) {
        this.root = c;
    }
    
    
    public void addCat(CatInfo c)
    {
        this.root = root.addCat(new CatNode(c));
    }
    
    public void removeCat(CatInfo c)
    {
        this.root = root.removeCat(c);
    }
    
    public int mostSenior()
    {
        return root.mostSenior();
    }
    
    public int fluffiest() {
        return root.fluffiest();
    }
    
    public CatInfo fluffiestFromMonth(int month) {
        return root.fluffiestFromMonth(month);
    }
    
    public int hiredFromMonths(int monthMin, int monthMax) {
        return root.hiredFromMonths(monthMin, monthMax);
    }
    
    public int[] costPlanning(int nbMonths) {
        return root.costPlanning(nbMonths);
    }
    
    
    
    public Iterator<CatInfo> iterator()
    {
        return new CatTreeIterator();
    }
    
    
    class CatNode {
        
        CatInfo data;
        CatNode senior;
        CatNode same;
        CatNode junior;
        
        public CatNode(CatInfo data) {
            this.data = data;
            this.senior = null;
            this.same = null;
            this.junior = null;
        }
        
        public String toString() {
            String result = this.data.toString() + "\n";
            if (this.senior != null) {
                result += "more senior " + this.data.toString() + " :\n";
                result += this.senior.toString();
            }
            if (this.same != null) {
                result += "same seniority " + this.data.toString() + " :\n";
                result += this.same.toString();
            }
            if (this.junior != null) {
                result += "more junior " + this.data.toString() + " :\n";
                result += this.junior.toString();
            }
            return result;
        }
        
        
        public CatNode addCat(CatNode c) {
        	
        	//check if the cat has more seniority than the to be added one
            if(this.data.monthHired > c.data.monthHired) {
            	if(this.senior == null) {
            		this.senior = c;
            	}
            	if(this.senior.data.monthHired == c.data.monthHired) {
            		this.senior.addCat(c);
            	}
            	if(this.senior.data.monthHired < c.data.monthHired) {
            		if(this.senior.junior == null) {
            			this.senior.addCat(c);
            		}
            		else {
            			this.senior.junior.addCat(c);
            		}
            	}
            	if(this.senior.data.monthHired > c.data.monthHired) {
            		this.senior.addCat(c);
            	}
            }
            
            //check if the cat has less seniority than the to be added one
            if(this.data.monthHired < c.data.monthHired) {
            	if(this.junior == null) {
            		this.junior = c;
            	}
            	if(this.junior.data.monthHired == c.data.monthHired) {
            		this.junior.addCat(c);
            	}
            	if(this.junior.data.monthHired > c.data.monthHired) {
            		if(this.junior.senior == null) {
            			this.junior.addCat(c);
            		}
            		else {
            			this.junior.senior.addCat(c);
            		}
            	}
            	if(this.junior.data.monthHired < c.data.monthHired) {
            		this.junior.addCat(c);
            	}
            }
            
            //check if the cat has the same seniority than the to be added one
            if(this.data.monthHired == c.data.monthHired) {
            	if(this.data.furThickness > c.data.furThickness) {
            		this.same = c;
            	}
            	if(this.data.furThickness < c.data.furThickness) {
            		CatNode temp1 = this.senior;
            		CatNode temp2 = this.junior;
            		this.senior = null;
            		this.junior = null;
            		c.senior = temp1;
            		c.junior = temp2;
            		c.same = this;
            		return c;
            	}
            }
            return this;
        }
        
        public CatNode removeCat(CatInfo c) {
        	
        	//if the to be removed one in the senior branch
        	if(c.monthHired < this.data.monthHired) {
        		if(this.senior == null) {
        			return this;
        		}
        		this.senior = this.senior.removeCat(c);
        	}
        	
        	//if the to be removed one in the junior branch
        	else if(c.monthHired > this.data.monthHired) {
        		if(this.junior == null) {
        			return this;
        		}
        		this.junior = this.junior.removeCat(c);
        	}
        	
        	//if the to be removed one in the root, three different cases as the instruction declared
        	if(this.data.equals(c) && this.same != null) {
        		CatNode tempSenior = this.senior;
        		CatNode tempJunior = this.junior;
        		CatNode newRoot = this.same;
        		newRoot.senior = tempSenior;
        		newRoot.junior = tempJunior;
        		this.senior = null;
        		this.junior = null;
        		this.same = null;
        		return newRoot;
        	}
        	else if(this.data.equals(c) && this.same == null && this.senior != null) {
        		CatNode tempJunior = this.junior;
        		CatNode newRoot = this.senior;
        		if(newRoot.junior != null) {
        			newRoot.junior.addCat(tempJunior);
        		}
        		else if(newRoot.junior == null) {
        			newRoot.junior = tempJunior;
        		}
        		this.senior = null;
        		this.junior = null;
        		this.same = null;
        		return newRoot;
        	}
        	else if(this.data.equals(c) && this.same == null && this.senior == null){
        		CatNode newRoot = this.junior;
        		this.junior = null;
        		this.senior = null;
        		this.same = null;
        		return newRoot;
        	}

        	//consider if one of cats in the same branch satisfy the condition, not at root
        	if (c.monthHired == this.data.monthHired){
        		this.same = this.same.removeCat(c);
        	}
        	
        	return this;
        }
        
        
        public int mostSenior() {
        	
        	//use the recursion to find the monthHired in the tree with the root this,
        	// until there is no more seniors
        	if(this.senior == null) {
        		return this.data.monthHired;
        	}
        	else {
        		return this.senior.mostSenior();
        	}
        }
        
        //helper function to return the max number between two numbers
        public int max(int x, int y) {
        	if(x > y) {
        		return x;
        	}
        	else {
        		return y;
        	}
        }
        
        public int fluffiest() {
            if(this.senior == null && this.junior == null && this.same == null) {
            	return this.data.furThickness;
            }
            else {
            	int thickness = this.data.furThickness;
            	if(this.senior != null) {
            		thickness = max(thickness, this.senior.fluffiest());
            	}
            	if(this.same != null) {
            		thickness = max(thickness, this.same.fluffiest());
            	}
            	if(this.junior != null) {
            		thickness = max(thickness, this.junior.fluffiest());
            	}
            	return thickness;
            }
        }
        
        
        public int hiredFromMonths(int monthMin, int monthMax) {
        	if(monthMin > monthMax) {
        		return 0;
        	}
        	int count = 0;
            if(this.data.monthHired <= monthMax && this.data.monthHired >= monthMin) {
            	count += 1;
            }
            //should be in outside of the if condition
            if(this.junior != null) {
        		count += this.junior.hiredFromMonths(monthMin, monthMax);
        	}
        	if(this.same != null) {
        		count += this.same.hiredFromMonths(monthMin, monthMax);
        	}
        	if(this.senior != null) {
        		count += this.senior.hiredFromMonths(monthMin, monthMax);
        	}
            return count;
        }
        
        public CatInfo fluffiestFromMonth(int month) {
            if(month > this.data.monthHired && this.junior != null) {
            	this.junior.fluffiestFromMonth(month);
            }
            else if(month < this.data.monthHired && this.senior != null) {
            	this.senior.fluffiestFromMonth(month);
            }
            else if(this.data.monthHired == month) {
            	return this.data;
            }
            return null;
        }
        
        public int[] costPlanning(int nbMonths) {
           int[] result = new int[nbMonths];
           result[this.data.nextGroomingAppointment - 243] += this.data.expectedGroomingCost;
           if(this.junior != null) {
        	   int indexOne = this.junior.data.nextGroomingAppointment - 243;
        	   result[indexOne] += this.junior.costPlanning(nbMonths)[indexOne];
           }
           if(this.same != null) {
        	   int indexTwo = this.same.data.nextGroomingAppointment - 243;
        	   result[indexTwo] += this.same.costPlanning(nbMonths)[indexTwo];
           }
           if(this.senior != null) {
        	   int indexThree = this.senior.data.nextGroomingAppointment - 243;
        	   result[indexThree] += this.senior.costPlanning(nbMonths)[indexThree];
           }
           return result;
        }
    }
   
    private class CatTreeIterator implements Iterator<CatInfo> {
    	ArrayList<CatNode> list;
        
        public CatTreeIterator() {
            list = new ArrayList<CatNode>();
            while(root != null) {
            	list.add(root);
            	root = root.senior;
            	
            }
        }
        
        public CatInfo next(){
        	
            CatNode cur = list.remove(list.size());
            CatInfo result = cur.data;
            if(cur.junior != null) {
            	cur = cur.junior;
            	while(cur != null) {
            		list.add(cur);
            		cur = cur.senior;
            	}
            }
            return result;
        }
        
        
        public boolean hasNext() {
            return !(list.isEmpty());
        }
    }
}

  

