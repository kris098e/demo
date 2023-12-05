import datetime as dt

def parse_isoformat(date):
    return dt.datetime.fromisoformat(date[:10]) + dt.timedelta(
            hours=int(date[11:13]),
            minutes=int(date[14:16]),
            seconds=int(date[17:19])
        )
    