#!/usr/bin/env Rscript

library(ggplot2)
library(gridExtra)
library(ggthemes)

script.dir <- dirname(sys.frame(1)$ofile)
setwd(script.dir);

map_facets<-c(
  "k-evaluation" = "Evaluation\nResearch", 
  "k-experience" = "Experience\nReport",
  "k-opinion" = "Opinion\nPaper",
  "k-philosophical" = "Philosophical\nPaper",
  "k-solution" = "Solution\nProposal",
  "k-validation" = "Validation\nResearch",
  "k-vis" = "Beyond product\nline analysis",
  "k-testing" = "Testing and\nevolution",
  "k-reverse" = "Reverse\nengineering",
  "k-mmodel" = "Multi-model\nvariability analysis",
  "k-modeling" = "Variability and\nmodeling expressivenes",
  "k-configuration" = "Product configuration\nand derivation"
)


#------------- Read data ----------

data = read.csv("../output_data/data_variability.csv", header = TRUE,sep=';')

#------------- Generate Graph ----------

plot<-ggplot(data, aes(x = factor(thing), y = count))  +
  theme_bw()+
  geom_bar(stat = "identity", position=position_dodge(),fill="gray", colour="black") +
  geom_text(aes(x=thing, y=count, label=count),vjust=1.5, size=10,
            position = position_dodge(width=0.95)) +
  scale_x_discrete(labels=map_facets)+
  scale_y_discrete(labels=map_facets)+
  ylab("Number of papers") + 
  xlab("Variability facet") + theme(text = element_text(size=20),axis.text.x = element_text(angle=60, hjust=1))
#------------- Write to pdf ----------

pdf("data_variability.pdf")
print(plot)
dev.off()



#------------- Read data ----------

data = read.csv("../output_data/data_research.csv", header = TRUE,sep=';')

#------------- Generate Graph ----------

plot<-ggplot(data, aes(x = factor(thing), y = count))  +
  theme_bw()+
  geom_bar(stat = "identity", position=position_dodge(),fill="gray", colour="black") +
  geom_text(aes(x=thing, y=count, label=count),vjust=ifelse(data$count <10 , 1.2, -0.3),size=10,
            position = position_dodge(width=0.95)) +
  scale_x_discrete(labels=map_facets)+
  scale_y_discrete(labels=map_facets)+
  ylab("Number of papers") + 
  xlab("Research facet") + theme(text = element_text(size=20),axis.text.x = element_text(angle=60, hjust=1))
#------------- Write to pdf ----------

pdf("data_research.pdf")
print(plot)
dev.off()

