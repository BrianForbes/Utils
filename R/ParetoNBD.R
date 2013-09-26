# TODO: Add comment
# 
# Author: brian.forbes
###############################################################################
require("BTYD")

getDate <- function(str)
{
	y <- as.integer(substring(str, 1, 4))
	m <- as.integer(substring(str, 5, 6))
	d <- as.integer(substring(str, 7, 8))
	return (as.Date(ISOdate(y,m,d) ) )
}

loadData <- function()
{
	fileName <- "C:/Users/brian.forbes/git/Utils/LTV/data/CDNOW_sample.txt"
	dat <- read.table(fileName, header=TRUE)
	dat$date <- getDate(dat$date)
	dat <- dc.MergeTransactionsOnSameDate(dat)
	return( dat )
}


dat <- loadData()

#Split off calibration period data
end.of.cal.period <- as.Date("1997-09-30")
dat.cal <- dat[which(dat$date <= end.of.cal.period), ]

# Data cleanup - looks like 
# 'split.data$repeat.trans.elog' just has all first transactions removed.
# So users with only 1 transaction are removed completely.
split.data <- dc.SplitUpElogForRepeatTrans(dat.cal)
clean.dat <- split.data$repeat.trans.elog

print(head(dat.cal,20))

print(head(split.data$repeat.trans.elog,20))

print(head(split.data$cust.data,20))

print("--------------------------------------------")

freq.cbt <- dc.CreateFreqCBT(clean.dat)
print(freq.cbt[1:3, 1:5])

tot.cbt <- dc.CreateFreqCBT(dat)
cal.cbt <- dc.MergeCustomers(tot.cbt, freq.cbt)


birth.periods <- split.data$cust.data$birth.per
last.dates <- split.data$cust.data$last.date
cal.cbs.dates <- data.frame(birth.periods, last.dates,
		end.of.cal.period)
cal.cbs <- dc.BuildCBSFromCBTAndDates(cal.cbt, cal.cbs.dates,
		per="week")

if(FALSE)
{
params <- pnbd.EstimateParameters(cal.cbs)
print(params)
LL <- pnbd.cbs.LL(params, cal.cbs)
print(LL)

#Number of expected repeat transactions for a new customer in 1 year
print( pnbd.Expectation(params, t = 52) )
}

