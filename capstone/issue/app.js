var createError = require('http-errors');
var express = require('express');
var path = require('path');
var cookieParser = require('cookie-parser');
var logger = require('morgan');
const multer = require('multer');

const update_image_url = require('./Function/update_image_url');

const storage = multer.diskStorage({//사진 한장 추가
	destination: function (req, file, cb) {
		let target = req.params.target;

		if(target === "parent"){
			cb(null, 'uploads/parent');
		}
		else if(target === "teacher"){
			cb(null, 'uploads/teacher');
		}
		else if(target === "president"){
			cb(null, 'uploads/president');
		}
		else if(target === "food"){
			cb(null, 'uploads/food');
		}
	},
	filename: function (req, file, cb) {
		let target = req.params.target;
		let key = req.params.key;
		let timestamp = new Date().getTime().valueOf();	// 현재 시간
		let file_url = timestamp + key + path.basename(file.originalname);
		update_image_url.update_image_url(target, key, file_url)
		cb(null, file_url);
	},
	limits: {fileSize: 1 * 256 * 256}
})
const storages = multer.diskStorage({//사진 여러장 추가
	destination: function (req, file, cb) {
		cb(null, 'uploads/album');
	},
	filename: function (req, file, cb) {
		let school = req.params.school;
		let room = req.params.room;
        let title = req.params.title;
        let date = req.params.date;

		let timestamp = new Date().getTime().valueOf();	// 현재 시간
		let file_url = timestamp + path.basename(file.originalname);

		update_image_url.insert_image_array_album(file_url, school, room, title, date)
		cb(null, file_url);
	},
	limits: {fileSize: 1 * 256 * 256}
})
const upload = multer({ storage: storage })
const uploads = multer({ storage: storages })

var indexRouter = require('./routes/index');
let checkRouter = require('./routes/check');
let createRouter = require('./routes/create');
let schoolmanagementRouter = require('./routes/schoolmanagement');
let uploadimageRouter = require('./routes/uploadimage');
let uploadimagesRouter = require('./routes/uploadimages');
let parentinfoRouter = require('./routes/parentinfo');
let presidentinfoRouter = require('./routes/presidentinfo');
let userRouter = require('./routes/user');
let food_listRouter = require('./routes/food_list');
let albumRouter = require('./routes/album');

//인승 추가(아래)
let staffRouter = require('./routes/staff');	// 선생, 원장 통일
let medicineRouter = require('./routes/medicine');	//약, 약 관리 통일
let testRouter = require('./routes/test1');
let alarmRouter = require('./routes/alarm');	//FCM 라우터
let calendarRouter = require('./routes/calendar');

var app = express();

// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'jade');

app.use(logger('dev'));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));
app.use('/image', express.static('uploads'));

app.use('/', indexRouter);
app.use('/check', checkRouter);
app.use('/create', createRouter);
app.use('/user', userRouter);
app.use('/schoolmanagement', schoolmanagementRouter);
app.use('/parentinfo', parentinfoRouter);
app.use('/presidentinfo', presidentinfoRouter);
app.use('/food_list', food_listRouter);
app.use('/album', albumRouter);
app.post('/uploadimage/:target/:key', upload.single('image'), uploadimageRouter);//사진 한장
app.post('/uploadimages/:school/:room/:title/:date', uploads.array('image'), uploadimagesRouter);//사진 배열
//인승 추가(아래)
app.use('/staff', staffRouter);	// 선생, 원장 통일
app.use('/medicine', medicineRouter); //약, 약 관리 통일
app.use('/test1', testRouter);
app.use('/alarm',alarmRouter);	//FCM 라우터
app.use('/calendar',calendarRouter);

// catch 404 and forward to error handler
app.get('/favicon.ico', function(req, res) { 
    res.status(204);
    res.end();    
});

app.use(function(req, res, next) {
  next(createError(404));
});

// error handler
app.use(function(err, req, res, next) {
  // set locals, only providing error in development
  res.locals.message = err.message;
  res.locals.error = req.app.get('env') === 'development' ? err : {};

  // render the error page
  res.status(err.status || 500);
  res.render('error');
});

module.exports = app;