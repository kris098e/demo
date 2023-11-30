import requests
import json
import os
import datetime as dt

from hashlib import sha256
from flask import request, Flask, render_template, redirect, url_for, session
from models import Show, Shift
from urllib.parse import quote_plus

app = Flask(__name__)
BASE_URL = 'http://localhost:8080/api'
app.secret_key = os.urandom(32)

@app.route('/', methods=['GET'])
def homepage():
    if 'jwt' not in session:
        return redirect(url_for('login_page'))
    # Get all shifts and shows
    today = quote_plus(str(dt.datetime.now(dt.timezone.utc).astimezone().isoformat()))
    token = session['jwt']
    r = requests.get(
        url=f'{BASE_URL}/shifts/all/{today}',
        headers={
            'Accept': 'application/json',
            'Authorization': f'{token}'
        }
    )

    if r.status_code != 200:
        print(r.text)
        return 'Something went wrong', 500

    response = json.loads(r.text)
    shows = set()
    for shift in response:
        shows.add(shift['show'])
    
    shows = {show['uuid']: Show(show['title'], []) for show in shows}

    for shift in response:
        shows[shift['show']['uuid']].shifts.append(
            Shift(
                shift['uuid'],
                shift['show']['uuid'],
                shift['show']['from'],
                shift['show']['to'],
                shift['role']
            )
        )
    
    shows = shows.values()
    for show in shows:
        show.shifts = sorted(show.shifts, key=lambda shift: dt.datetime.fromisoformat(shift.start))
    
    return render_template('index.html', shows=shows, user='', admin=False)

@app.route('/login/', methods=['GET'])
def login_page():
    return render_template('login.html', user='', admin=False)

@app.route('/login/', methods=['POST'])
def login_request():
    attempted_username = request.form['username']
    attempted_password = request.form['password']
    attempted_password = sha256(attempted_password.encode()).hexdigest()
    user = {'username': attempted_username, 'password': attempted_password}
    r = requests.post(
        url=f'{BASE_URL}/users/login',
        headers={'content-type': 'application/json'},
        data=json.dumps(user)
    )

    if r.status_code == 200:
        session['jwt'] = r.text
        return redirect(url_for('homepage'))
    else:
        print(r.status_code, r.text, sep='\t')
        error = 'Invalid Credentials'
        return render_template('login.html', error=error, user='', admin=False)


if __name__ == '__main__':
    app.run('127.0.0.1', 4000)