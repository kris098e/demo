import datetime as dt

def parse_isoformat(date):
    d = dt.datetime.fromisoformat(date[:10]) + dt.timedelta(
            hours=int(date[11:13]),
            minutes=int(date[14:16]),
            seconds=int(date[17:19])
        )
    return f'{d.day}/{d.month}/{d.year} - {d.hour}:{d.minute if d.minute > 9 else "0" + str(d.minute)}'