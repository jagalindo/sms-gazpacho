#!/usr/bin/env Rscript

library(ggplot2)
library(gridExtra)
library(ggthemes)

#script.dir <- dirname(sys.frame(1)$ofile)
#setwd(script.dir);
#------------- Read data ----------

data = read.csv("./heatmap.csv", header = TRUE,sep=';')

map_facets<-c(
  "SU"="Survey",
  "C11"="C11 - Software quality",
  "C10"="C10 - Software engineering tools and methods",
  "C12"="C12 - Related Disciplines of Software Engineering",
  "C2"="C2 - Software requirements",
  "CS"="Case Study",
  "C3"="C3 - Software design",
  "C4"="C4 - Software construction",
  "C5"="C5 - Software testing",
  "EX"="Experiment",
  "C6"="C6 - Software maintenance",
  "QE"="Quasi-experiment",
  "C7"="C7 - Software configuration management",
  "C8"="C8 - Software engineering management",
  "C9"="C9 - Software engineering process"
)

#Order like in the rest of sections
#data$FacetA <- factor(data$FacetA, levels = c("C2","C3","C4","C5","C6","C7","C8","C9","C10","C11","C12"))
#data$FacetB <- factor(data$FacetB, levels = c("SU","QE","EX","CS"))

plot<-ggplot(data, aes(x=faceta, y=facetb)) + 
  geom_tile(aes(fill = papers),colour = "white") + 
  geom_text(aes(label = papers),size=5) +
  scale_fill_gradient(high="#707070", low="white",name = "Number of papers") +
  ylab("Types of empirical study used") + 
  xlab("Knowledge Areas (KAs)")+ 
  theme(legend.position="right") +
  scale_x_discrete(labels=map_facets)+
  scale_y_discrete(labels=map_facets)+
  theme(axis.text=element_text(size=7), axis.title=element_text(size=9,face="bold"))+
  theme(panel.grid.major = element_blank(),
        panel.grid.minor = element_blank(), 
        panel.background = element_blank())+ theme(aspect.ratio=0.5)+
  theme(axis.text.x=element_text(angle=45,hjust=1)) 

#------------- Write to pdf ----------

ggsave("./heatmap.pdf", plot, width = 10, height = 4)